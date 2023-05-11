package icu.resip.service;

import icu.resip.pay.qo.OrderQo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author Peng
 * @Date 2022/4/9
 */
public interface PayService {

    /**
     * 调用微信支付创建预订单
     * @param money 要充值的金额
     * @param token 用户的身份令牌
     * @return 微信小程序调用微信支付所需参数及订单号
     */
    Map<String, Object> createOrder(BigDecimal money, String token);

    /**
     * 修改订单
     * @param orderQo
     */
    void updateOrder(OrderQo orderQo);

    /**
     * 查询用户钱包余额
     * @param token
     * @return
     */
    String findBalance(String token);

    /**
     * 设置钱包密码
     * @param token
     * @param passwd
     */
    void setWalletPasswd(String token, String passwd);

    /**
     * 支付回调进入修改订单逻辑
     * @param request
     * @return
     */
    String callback(HttpServletRequest request);

    /**
     * 获取钱包密码
     * @param token
     * @return
     */
    String getWalletPasswd(String token);

    /**
     * 检查用户是否已设置钱包密码
     * @param token
     * @return 已设置-true
     */
    boolean checkSetPasswd(String token);
}
