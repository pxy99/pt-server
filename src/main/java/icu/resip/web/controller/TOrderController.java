package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.captcha.service.CaptchaService;
import icu.resip.constants.CommonConstants;
import icu.resip.entity.OrderDetail;
import icu.resip.entity.OrderVo;
import icu.resip.exception.LogicException;
import icu.resip.qo.ReceiveQo;
import icu.resip.qo.TOrderQo;
import icu.resip.qo.ThingQo;
import icu.resip.result.CommonCodeMsg;
import icu.resip.result.Result;
import icu.resip.service.TOrderService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帮我取订单资源控制器
 * @Author Peng
 * @Date 2022/4/13
 */
@RestController
@RequestMapping("/api/order/take")
public class TOrderController {

    private TOrderService orderService;

    @Autowired
    public void setOrderService(TOrderService orderService) {
        this.orderService = orderService;
    }

    private CaptchaService captchaService;

    @Autowired
    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    // 下单
    @CheckToken
    @PostMapping("/place")
    public Result<Long> place(@RequestBody TOrderQo orderQo, HttpServletRequest request) {
        // 校验参数
        List<ThingQo> thingQoList = orderQo.getThingQoList();
        if (thingQoList != null && thingQoList.size() > 0) {
            AssertUtil.isParamsNull(thingQoList.get(0).getStoreName());
            List<String> descList = thingQoList.get(0).getDescList();
            if (descList == null || descList.size() == 0) {
                throw new LogicException(CommonCodeMsg.NULL_PARAM);
            }
        } else {
            throw new LogicException(CommonCodeMsg.NULL_PARAM);
        }
        AssertUtil.isParamsNull(orderQo.getPasswd(), orderQo.getThingId(), orderQo.isUpstairs(), orderQo.getWeight(), orderQo.getPickName(), orderQo.getPickPhone(), orderQo.getPickAddress(), orderQo.getReceiveId(), orderQo.getReceiveAddress(), orderQo.getExpireTime());

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 下单
        Long id = orderService.place(orderQo, token);
        return Result.success(id);
    }

    // 订单列表
    @CheckToken
    @GetMapping(value = {"/list", "/list/{status}"})
    public Result<List<OrderVo>> list(@PathVariable(value = "status", required = false) Integer status, HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 根据条件查询所有自己的订单信息
        List<OrderVo> orderVoList = orderService.listByStatus(status, token);

        return Result.success(orderVoList);
    }

    // 订单详情
    @CheckToken
    @GetMapping("/list/detail/{id}")
    public Result<OrderDetail> listDetail(@PathVariable("id") Long id, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(id);
        if (id <= 0) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_CONDITION);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 根据不同的订单状态查询订单详情
        OrderDetail orderDetail = orderService.listDetailByStatus(id, token);

        return Result.success(orderDetail);
    }

    // 接单
    @CheckToken
    @PutMapping("/receive")
    public Result<Object> receive(@RequestBody ReceiveQo receiveQo, HttpServletRequest request) {
        // 二次校验验证码
        if (!captchaService.checkCaptchaAgain(receiveQo.getCaptchaVerification())) {
            throw new LogicException(CommonCodeMsg.HUMAN_VERIFY_ERROR);
        }

        // 校验参数
        AssertUtil.isParamsNull(receiveQo.getId());
        if (receiveQo.getId() <= 0) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_CONDITION);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 跑腿员接单
        orderService.receive(receiveQo.getId(), token);

        return Result.success(null);
    }

    // 取消订单
    @CheckToken
    @PutMapping("/cancel")
    public Result<Object> cancel(@RequestBody ReceiveQo receiveQo, HttpServletRequest request) {
        // 二次校验验证码
        if (!captchaService.checkCaptchaAgain(receiveQo.getCaptchaVerification())) {
            throw new LogicException(CommonCodeMsg.HUMAN_VERIFY_ERROR);
        }

        // 校验参数
        AssertUtil.isParamsNull(receiveQo.getId());
        if (receiveQo.getId() <= 0) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_CONDITION);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 用户自己或跑腿员取消订单
        orderService.cancel(receiveQo.getId(), token);

        return Result.success(null);
    }

    // 订单续约
    @CheckToken
    @PutMapping("/renewal")
    public Result<Object> renewal(@RequestBody ReceiveQo receiveQo, HttpServletRequest request) {
        // 二次校验验证码
        if (!captchaService.checkCaptchaAgain(receiveQo.getCaptchaVerification())) {
            throw new LogicException(CommonCodeMsg.HUMAN_VERIFY_ERROR);
        }

        // 校验参数
        AssertUtil.isParamsNull(receiveQo.getId(), receiveQo.getTime());
        if (receiveQo.getId() <= 0) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_CONDITION);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 给订单续约
        orderService.renewal(receiveQo, token);

        return Result.success(null);
    }

    // 确认送达
    @CheckToken
    @PutMapping("/confirm")
    public Result<Object> confirm(@RequestBody ReceiveQo receiveQo, HttpServletRequest request) {
        // 二次校验验证码
        if (!captchaService.checkCaptchaAgain(receiveQo.getCaptchaVerification())) {
            throw new LogicException(CommonCodeMsg.HUMAN_VERIFY_ERROR);
        }

        // 校验参数
        AssertUtil.isParamsNull(receiveQo.getId(), receiveQo.getReceiveCode());
        if (receiveQo.getId() <= 0) {
            throw new LogicException(CommonCodeMsg.ILLEGAL_CONDITION);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 跑腿员确认送达，更改订单状态，接单时间
        orderService.confirm(receiveQo, token);

        return Result.success(null);
    }

}
