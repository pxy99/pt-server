package icu.resip.constants;

/**
 * @Author Peng
 * @Date 2022/3/26
 */
public class CommonConstants {

    // 登录令牌key
    public static final String TOKEN_NAME = "token";

    public static final String REAL_IP = "X-REAL-IP";

    public static final Long TRUE = 1L;

    public static final Long FALSE = 0L;

    /**
     * 文本
     */
    public static final int MESSAGE_TYPE_TEXT = 0;

    /**
     * 图片
     */
    public static final int MESSAGE_TYPE_IMAGE = 1;

    /**
     * 未认证
     */
    public static final int UN_AUTH = 0;

    /**
     * 待认证
     */
    public static final int TO_BE_AUTH = 1;

    /**
     * 已认证
     */
    public static final int AUTH_ED = 2;

    /**
     * 待支付
     */
    public static final int TO_BE_PAY = 0;

    /**
     * 支付成功
     */
    public static final int PAYED = 1;

    /**
     * 支付失败
     */
    public static final int PAY_ERROR = 2;

    // 微信小程序token有效时间，单位分
    public static final int WX_LOGIN_TOKEN_VAI_TIME = 60 * 24 * 7;

    // 微信小程序session_key有效时间，单位分
    public static final int WX_SESSION_KEY_VAI_TIME = 60 * 24 * 7;

    // 邮箱验证码有效时间，单位分
    public static final int VERIFY_CODE_VAI_TIME = 10;

    // 图片存放目录
    public static final String PICTURE_DIRECTORY = "/usr/pic/";

    // 外网访问图片路径
    public static final String PICTURE_URL = "https://xypt.imgs.space/images/";

    // 未认证角色sn
    public static final String UNAUTH = "UnAuth";

    // 认证角色sn
    public static final String AUTHED = "Authed";

}
