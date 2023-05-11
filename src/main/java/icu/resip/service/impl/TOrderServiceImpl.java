package icu.resip.service.impl;

import cn.hutool.core.util.IdUtil;
import icu.resip.constants.CommonConstants;
import icu.resip.constants.OrderConstants;
import icu.resip.domain.order.TOrder;
import icu.resip.domain.order.TOrderThing;
import icu.resip.entity.OrderDetail;
import icu.resip.entity.OrderVo;
import icu.resip.entity.UserInfo;
import icu.resip.exception.LogicException;
import icu.resip.mapper.TOrderMapper;
import icu.resip.mapper.UserMapper;
import icu.resip.qo.ReceiveQo;
import icu.resip.qo.TOrderQo;
import icu.resip.qo.ThingQo;
import icu.resip.redis.service.OrderRedisService;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.PayService;
import icu.resip.service.TOrderService;
import icu.resip.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author Peng
 * @Date 2022/4/14
 */
@Service
public class TOrderServiceImpl implements TOrderService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    private OrderRedisService orderRedisService;

    @Autowired
    public void setOrderRedisService(OrderRedisService orderRedisService) {
        this.orderRedisService = orderRedisService;
    }

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    private TOrderMapper orderMapper;

    @Autowired
    public void setOrderMapper(TOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    private PayService payService;

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @Transactional
    @Override
    public Long place(TOrderQo orderQo, String token) {
        // 查询用户钱包密码，md5加密后
        String passwd = payService.getWalletPasswd(token);
        if (StringUtils.isEmptyOrNull(passwd)) {
            throw new LogicException(CommonCodeMsg.NOT_SET_WALLET_PASSWD);
        }

        // 给用户输入的钱包密码md5加密
        String md5Passwd = DigestUtils.md5DigestAsHex(orderQo.getPasswd().getBytes());

        // 校验用户钱包密码
        if (!passwd.equalsIgnoreCase(md5Passwd)) {
            throw new LogicException(CommonCodeMsg.PASSWD_VERIFY_ERROR);
        }

        // 校验订单总金额
        BigDecimal money = new BigDecimal(orderQo.getMoney());
        if (money.compareTo(BigDecimal.ZERO) < 0) {
            throw new LogicException(CommonCodeMsg.MONEY_ERROR);
        }

        // 查询用户余额
        String balance = payService.findBalance(token);
        BigDecimal bigDecimal = new BigDecimal(balance);
        if (bigDecimal.compareTo(money) < 0) {
            throw new LogicException(CommonCodeMsg.MONEY_NOT_ENOUGH);
        }

        // 使用雪花算法生成19位字符串作为订单号
        String orderNo = String.valueOf(IdUtil.getSnowflake().nextId());

        // 订单过期时间
        Date now = new Date();
        long time = orderQo.getExpireTime() * 1000;
        Date expireTime = new Date(now.getTime() + time);

        // 计算物品数量
        int amount = 0;
        // 拼接物品信息
        List<TOrderThing> orderThings = new ArrayList<>();
        List<ThingQo> thingQoList = orderQo.getThingQoList();
        for (ThingQo thingQo : thingQoList) {
            TOrderThing orderThing = new TOrderThing();
            orderThing.setStoreName(thingQo.getStoreName());
            List<String> descList = thingQo.getDescList();
            StringBuilder descs = new StringBuilder();
            for (int i = 0; i < descList.size(); i++) {
                String desc = descList.get(i);
                descs.append(desc);
                if (i != descList.size() - 1)
                    descs.append("|");
                amount++;
            }
            orderThing.setDesc(descs.toString());
            orderThings.add(orderThing);
        }

        // 生成四位数收获码
        String receiveCode = StringUtils.generateNum(4);

        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 封装订单数据
        TOrder order = new TOrder();
        order.setOrderNo(orderNo);
        order.setUid(uid);
        order.setThingId(orderQo.getThingId());
        order.setAmount(amount);
        order.setUpstairs(orderQo.isUpstairs());
        order.setWeight(orderQo.getWeight());
        order.setPs(orderQo.getPs());
        order.setPickName(orderQo.getPickName());
        order.setPickPhone(orderQo.getPickPhone());
        order.setPickAddress(orderQo.getPickAddress());
        order.setReceiveId(orderQo.getReceiveId());
        order.setReceiveAddress(orderQo.getReceiveAddress());
        order.setExpireTime(expireTime);
        order.setMoney(money);
        order.setPayType(orderQo.getPayType());
        order.setReceiveCode(receiveCode);
        order.setCreateTime(now);
        order.setStatus(OrderConstants.TO_BE_ORDER);

        // 保存订单
        int raw = orderMapper.insertOrder(order);
        if (raw == 0) {
            throw new LogicException(CommonCodeMsg.CREATE_ORDER_ERROR);
        }

        // 保存物品信息
        for (TOrderThing orderThing : orderThings) {
            orderThing.setTakeOrderId(order.getId());
            int count = orderMapper.insertTakeOrderThing(orderThing);
            if (count == 0) {
                throw new LogicException(CommonCodeMsg.CREATE_ORDER_ERROR);
            }
        }

        // 减去用户响应的金额
        BigDecimal decimal = bigDecimal.subtract(money);
        int c = userMapper.updateBalance(uid, decimal);
        if (c == 0) {
            throw new LogicException(CommonCodeMsg.CREATE_ORDER_ERROR);
        }

        // 在一定时间内保存订单到redis，时间到了，redis中key过期，会通知，执行订单取消计划
        orderRedisService.saveTOrder(orderNo, orderQo.getExpireTime());

        return order.getId();
    }

    @Override
    public List<OrderVo> listByStatus(Integer status, String token) {
        // 如果有条件，校验条件
        if ((status != null) && (status < 0 || status > 4)) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_CONDITION);
        }

        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 拼接where语句
        StringBuilder sb = new StringBuilder();
        if (status == null) {
            sb.append(" where (tao.uid = ").append(uid).append(" or tao.receive_uid = ").append(uid).append(") ");
            sb.append(" order by tao.status asc, tao.create_time desc, tao.expire_time asc ");
        } else {
            sb.append(" where tao.uid = ").append(uid);
            switch (status) {
                case 0: sb.append(" and tao.status = 0 order by tao.create_time desc ");
                    break;
                case 1: sb.append(" and tao.status = 1 order by tao.receive_time desc ");
                    break;
                case 2: sb.append(" and tao.status = 2 order by tao.complete_time desc ");
                    break;
                case 3: sb.append(" and tao.status = 3 order by tao.cancel_time desc ");
                    break;
                case 4:
                    sb = new StringBuilder();
                    sb.append(" where tao.receive_uid = ").append(uid);
                    sb.append(" and (tao.status = 1 or tao.status = 2 or tao.status = 3) order by tao.status asc, tao.receive_time desc, tao.cancel_time desc ");
                    break;
                default:
                    sb = new StringBuilder();
                    sb.append(" where (tao.uid = ").append(uid).append(" or tao.receive_uid = ").append(uid).append(") ");
                    sb.append(" order by tao.status asc, tao.create_time desc, tao.expire_time asc ");
                    break;
            }
        }

        // 根据条件查询订单列表
        List<OrderVo> orderVoList = orderMapper.listByStatus(sb.toString());

        // 优化返回结果
        for (OrderVo orderVo : orderVoList) {
            // 如果是自己的配送单
            if (Objects.equals(uid, orderVo.getReceiveUid())) {
                orderVo.setReceiveCode(null);
                // 如果是正在配送的订单
                if (OrderConstants.ORDERED == orderVo.getStatus()) {
                    orderVo.setStatus(2);
                } else if (OrderConstants.SUCCESS == orderVo.getStatus()) {
                    // 如果是已完成的订单
                    orderVo.setStatus(4);
                } else if (OrderConstants.CANCELED == orderVo.getStatus()) {
                    // 如果是已取消的订单
                    orderVo.setStatus(5);
                }
            } else {
                // 如果是自己下的单已完成
                if (OrderConstants.SUCCESS == orderVo.getStatus()) {
                    orderVo.setStatus(3);
                } else if (OrderConstants.CANCELED == orderVo.getStatus()) {
                    // 如果是自己的单已取消
                    orderVo.setStatus(5);
                }
            }
        }

        return orderVoList;
    }

    @Override
    public OrderDetail listDetailByStatus(Long id, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 检查自己是否是发布者或接单者
        TOrder order = orderMapper.selectByUid(id, uid);
        if (order == null) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_OPERATION);
        }

        // 根据订单id查询订单详情
        OrderDetail orderDetail = orderMapper.listDetailByStatus(id);

        // 优化返回结果
        if (Objects.equals(uid, orderDetail.getReceiveUid()) && !Objects.equals(uid, orderDetail.getUid())) {
            // 如果这单是我配送的并且这单不是我发布的，不能让我看到收货码
            orderDetail.setReceiveCode(null);
        }

        return orderDetail;
    }

    @Override
    public void receive(Long id, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 验证跑腿员资质
        UserInfo userInfo = userMapper.selectUserInfo(uid);
        if (!Objects.equals(userInfo.getAuth(), CommonConstants.AUTH_ED)) {
            throw new LogicException(CommonCodeMsg.UN_AUTH);
        }

        // 更改订单状态为已接单，接单用户为自己，接单时间
        TOrder order = new TOrder();
        order.setReceiveUid(uid);
        order.setId(id);
        order.setStatus(OrderConstants.ORDERED);
        order.setReceiveTime(new Date());
        int raw = orderMapper.receiveOrder(order);
        if (raw == 0) {
            throw new LogicException(CommonCodeMsg.RECEIVE_ORDER_ERROR);
        }
    }

    @Override
    public void cancel(Long id, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 检查自己是否是发布者或接单者
        TOrder order = orderMapper.selectByUid(id, uid);
        if (order == null) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_OPERATION);
        }

        // 取消订单，更改订单状态为已取消，取消时间
        order.setId(id);
        order.setStatus(OrderConstants.CANCELED);
        order.setCancelTime(new Date());
        int raw = orderMapper.cancelOrder(order);
        if (raw == 0) {
            throw new LogicException(CommonCodeMsg.CANCEL_ORDER_ERROR);
        }
    }

    @Override
    public void renewal(ReceiveQo receiveQo, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 检查自己是否是发布者或接单者
        TOrder order = orderMapper.selectByUid(receiveQo.getId(), uid);
        if (order == null) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_OPERATION);
        }

        // 检查订单是否过期没有取消订单
        long l = order.getExpireTime().getTime() - new Date().getTime();
        if (l <= 0) {
            throw new LogicException(CommonCodeMsg.ORDER_EXPIRE);
        }

        // 更新redis中订单过期时间
        orderRedisService.reSetExpireTime(order.getOrderNo(), receiveQo.getTime());
    }

    @Override
    public void confirm(ReceiveQo receiveQo, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 检查自己是否是发布者或接单者
        TOrder order = orderMapper.selectByUid(receiveQo.getId(), uid);
        if (order == null) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_OPERATION);
        }

        // 校验收获码是否正确
        if (!receiveQo.getReceiveCode().equalsIgnoreCase(order.getReceiveCode())) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_RECEIVE_CODE);
        }

        // 更改订单状态为已完成，完成时间
        order.setId(receiveQo.getId());
        order.setStatus(OrderConstants.SUCCESS);
        order.setCompleteTime(new Date());
        int raw = orderMapper.confirmOrder(order);
        if (raw == 0) {
            throw new LogicException(CommonCodeMsg.CONFIRM_ORDER_ERROR);
        }

        // 给跑腿员加钱
        BigDecimal money = order.getMoney();
        BigDecimal decimal = userMapper.selectBalance(uid);
        BigDecimal balance = decimal.add(money);
        int c = userMapper.updateBalance(uid, balance);
        if (c == 0) {
            throw new LogicException(CommonCodeMsg.CONFIRM_ORDER_ERROR);
        }
    }

}
