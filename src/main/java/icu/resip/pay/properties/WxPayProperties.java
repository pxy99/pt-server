package icu.resip.pay.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Peng
 * @Date 2022/4/8
 */
@Getter
public class WxPayProperties {

    /**
     * 小程序appid
     */
    private final String appid = "";

    /**
     * 小程序secret
     */
    private final String secret = "";

    /**
     * 小程序支付网关
     */
    private final String gateway = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 商户号
     */
    private final String mchid = "";

    /**
     * 商户key
     */
    private final String mchkey = "";

    /**
     * 支付成功回调url
     */
    private final String notifyPath = "https://xypt.imgs.space/api/pay/callback";

}
