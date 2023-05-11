package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.constants.CommonConstants;
import icu.resip.exception.LogicException;
import icu.resip.pay.qo.OrderQo;
import icu.resip.result.CommonCodeMsg;
import icu.resip.result.Result;
import icu.resip.service.PayService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 微信支付资源控制器
 * @Author Peng
 * @Date 2022/4/9
 */
@RestController
@RequestMapping("/api/pay")
public class PayController {

    private PayService payService;

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    // 充值
    @CheckToken
    @PostMapping("/recharge/{money}")
    public Result<Map<String, Object>> recharge(@PathVariable("money") String money, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(money);

        // 检查money是否小于0.01
        BigDecimal decimal = new BigDecimal(money);
        if (decimal.compareTo(new BigDecimal("0.01")) < 0) {
            throw new LogicException(CommonCodeMsg.MONEY_ERROR);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 调用微信支付创建预订单
        Map<String, Object> data = payService.createOrder(decimal, token);
        return Result.success(data);
    }

    // 支付回调
    @RequestMapping(value = "/callback", produces = MediaType.APPLICATION_XML_VALUE)
    public String callback(HttpServletRequest request) {
        // 进入支付回调方法
        return payService.callback(request);
    }

    // 支付失败，修改订单状态为失败
    @CheckToken
    @PutMapping("/error")
    public Result<Object> error(@RequestBody OrderQo orderQo) {
        // 校验参数
        AssertUtil.isParamsNull(orderQo.getOrderNo(), orderQo.getContent());

        // 修改订单状态为失败
        payService.updateOrder(orderQo);

        return Result.success(null);
    }

    // 查询钱包余额
    @CheckToken
    @GetMapping("/balance")
    public Result<String> getBalance(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 查询用户钱包余额
        String balance = payService.findBalance(token);

        return Result.success(balance);

    }

    // 设置六位数钱包密码
    @CheckToken
    @PutMapping("/passwd/{passwd}")
    public Result<Object> setPasswd(@PathVariable("passwd") String passwd, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(passwd);

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 设置钱包密码
        payService.setWalletPasswd(token, passwd);

        return Result.success(null);
    }

    // 检查用户是否已设置钱包密码，已设置-true
    @CheckToken
    @GetMapping("/passwd/set/check")
    public Result<Boolean> checkSet(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 检查用户是否已设置钱包密码
        boolean isSet = payService.checkSetPasswd(token);

        return Result.success(isSet);
    }

}
