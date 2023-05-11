package icu.resip.service.impl;

import cn.hutool.core.util.IdUtil;
import icu.resip.constants.CommonConstants;
import icu.resip.exception.LogicException;
import icu.resip.mapper.PayMapper;
import icu.resip.mapper.UserMapper;
import icu.resip.pay.domain.Order;
import icu.resip.pay.properties.WxPayProperties;
import icu.resip.pay.qo.OrderQo;
import icu.resip.pay.util.HttpUtils;
import icu.resip.pay.util.WxPayUtil;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.PayService;
import icu.resip.utils.RequestUtil;
import icu.resip.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Peng
 * @Date 2022/4/9
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    private PayMapper payMapper;

    @Autowired
    public void setPayMapper(PayMapper payMapper) {
        this.payMapper = payMapper;
    }

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public Map<String, Object> createOrder(BigDecimal money, String token) {
        // 获取openid及uid
        String openid = userRedisService.getWxOpenid(token);
        Long uid = userRedisService.getWxUid(token);

        // 使用雪花算法生成19位字符串作为订单号
        String orderNo = String.valueOf(IdUtil.getSnowflake().nextId());

        // 商品名称
        String name = "钱包充值-" + money.toString();

        // 微信支付相关配置
        WxPayProperties payProperties = new WxPayProperties();

        // 封装请求微信支付网关所需参数
        log.info("----------------开始封装微信支付所需参数-----------------");
        SortedMap<String, String> params = new TreeMap<>();
        params.put("openid", openid);
        params.put("appid", payProperties.getAppid());
        params.put("mch_id", payProperties.getMchid());
        params.put("nonce_str", IdUtil.simpleUUID());
        params.put("sign_type", "MD5");
        params.put("body", name);
        params.put("out_trade_no", orderNo);
        params.put("fee_type", "CNY");
        params.put("total_fee", money.multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString());
        params.put("spbill_create_ip", RequestUtil.getIPAddress());
        params.put("trade_type", "JSAPI");
        params.put("notify_url", payProperties.getNotifyPath());
        params.put("sign", WxPayUtil.createSign(params, payProperties.getMchkey()));

        try {
            // map转成xml，请求微信支付网关，获取微信小程序调用支付模块所需参数
            log.info("---------------------封装参数完成，正在转成xml-----------------------");
            String paramsXml = WxPayUtil.mapToXml(params);

            log.info("----------------------转成xml完成，正在请求微信支付网关-------------------------");
            log.info("{}", paramsXml);
            String s = HttpUtils.doPost(payProperties.getGateway(), paramsXml);

            if (!StringUtils.isEmptyOrNull(s)) {
                log.info("----------------------请求成功，获得到响应数据-------------------------");
                log.info("{}", s);

                // 将xml转成map
                Map<String, String> map = WxPayUtil.xmlToMap(s);
                if (!map.isEmpty()) {
                    String resultCode = map.get("result_code");
                    if ("SUCCESS".equals(resultCode)) {
                        // 封装小程序调用支付模块所需参数
                        SortedMap<String, String> payment = new TreeMap<>();
                        payment.put("appId", payProperties.getAppid());
                        payment.put("nonceStr", map.get("nonce_str"));
                        payment.put("package", "prepay_id=" + map.get("prepay_id"));
                        payment.put("signType", "MD5");
                        payment.put("timeStamp", System.currentTimeMillis() + "");
                        payment.put("paySign", WxPayUtil.createSign(payment, payProperties.getMchkey()));
                        // 移除掉appId，不返回
                        payment.remove("appId");

                        // 创建充值订单，返回订单号，用于支付回调时修改订单状态
                        Order order = new Order();
                        order.setOrderNo(orderNo);
                        order.setUid(uid);
                        order.setName(name);
                        order.setMoney(money);
                        order.setStatus(CommonConstants.TO_BE_PAY);
                        order.setReason("待支付");
                        order.setCreateTime(new Date());
                        order.setPayTime(new Date());
                        payMapper.insert(order);

                        // 封装返回数据
                        Map<String, Object> data = new HashMap<>();
                        data.put("payment", payment);
                        data.put("orderNo", orderNo);
                        return data;
                    }
                }

            }

        } catch (Exception e) {
            throw new LogicException(CommonCodeMsg.PAY_ERROR);
        }

        return null;
    }

    @Override
    public void updateOrder(OrderQo orderQo) {
        // 查询订单信息
        Order order = payMapper.selectOne(orderQo.getOrderNo());

        // 判断此订单是否处理过，如果处理过，不再处理
        if (order == null) {
            throw new LogicException(CommonCodeMsg.NO_THIS_ORDER);
        }
        if (order.getStatus() == CommonConstants.PAYED) {
            throw new LogicException(CommonCodeMsg.PAY_SUCCESSED);
        }

        // 更改订单状态为失败，填写失败原因及支付时间
        order.setStatus(CommonConstants.PAY_ERROR);
        order.setReason(orderQo.getContent());
        order.setPayTime(new Date());
        int raw = payMapper.updateOrder(order);
        if (raw == 0) {
            throw new LogicException(CommonCodeMsg.ORDER_EDIT_ERROR);
        }
    }

    @Override
    public String findBalance(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);
        // 查询余额
        BigDecimal balance = userMapper.selectBalance(uid);
        if (balance != null) {
            return balance.toString();
        }
        return null;
    }

    @Override
    public void setWalletPasswd(String token, String passwd) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 给密码md5加密，存储到mysql中
        String pass = DigestUtils.md5DigestAsHex(passwd.getBytes());
        userMapper.updatePasswd(pass, uid);
    }

    @Transactional
    @Override
    public String callback(HttpServletRequest request) {
        // 微信支付相关配置
        WxPayProperties payProperties = new WxPayProperties();

        // 获取微信支付回调响应的xml字符串
        String responseStr = HttpUtils.getResponseStr(request);

        try {
            if (!StringUtils.isEmptyOrNull(responseStr)) {
                // 将xml转成map
                Map<String, String> map = WxPayUtil.xmlToMap(responseStr);

                if (!map.isEmpty()) {
                    // 将hashmap转成treemap
                    SortedMap<String, String> treeMap = new TreeMap<>(map);

                    // 校验签名
                    boolean isCorrectSign = WxPayUtil.isCorrectSign(treeMap, payProperties.getMchkey());
                    if (isCorrectSign) {
                        // 判断业务结果是否为SUCCESS
                        String resultCode = treeMap.get("result_code");
                        if ("SUCCESS".equals(resultCode)) {
                            // 取出交易单号和订单编号
                            String transactionId = treeMap.get("transaction_id");
                            String outTradeNo = treeMap.get("out_trade_no");

                            // 查询订单信息
                            Order order = payMapper.selectOne(outTradeNo);

                            if (order != null) {
                                // 判断此订单是否处理过了，处理过了不再处理，返回成功报文
                                if (order.getStatus() == CommonConstants.PAYED) {
                                    return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                                }

                                // 修改交易单号、订单状态、原因及支付时间
                                order.setTransactionId(transactionId);
                                order.setStatus(CommonConstants.PAYED);
                                order.setReason("支付成功");
                                order.setPayTime(new Date());
                                int raw = payMapper.updateOrder(order);

                                // 支付成功，给用户增加余额
                                if (raw == 1) {
                                    // 查询用户余额
                                    BigDecimal balance = userMapper.selectBalance(order.getUid());

                                    // 更新用户余额
                                    BigDecimal money = balance.add(order.getMoney());
                                    userMapper.updateBalance(order.getUid(), money);

                                    // 返回成功报文
                                    return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                                }
                            }

                        }
                    }

                }

            }
        } catch (Exception e) {
            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
        }

        // 返回失败报文
        return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml>";
    }

    @Override
    public String getWalletPasswd(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询钱包密码
        return userMapper.findPasswd(uid);
    }

    @Override
    public boolean checkSetPasswd(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询user表的钱包密码
        String passwd = userMapper.findPasswd(uid);

        return !StringUtils.isEmptyOrNull(passwd);
    }

}
