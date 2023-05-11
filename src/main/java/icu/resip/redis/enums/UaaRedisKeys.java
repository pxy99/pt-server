package icu.resip.redis.enums;

import icu.resip.constants.CommonConstants;
import lombok.Getter;

/**
 * @Author Peng
 * @Date 2021/11/30 19:42
 * @Version 1.0
 */
@Getter
public enum UaaRedisKeys {

    // 微信小程序用户登录令牌token
    WX_LOGIN_TOKEN("wx_login_token", CommonConstants.WX_LOGIN_TOKEN_VAI_TIME * 60L),

    // 登录用户信息和权限信息
    WX_USER("wx_user", CommonConstants.WX_LOGIN_TOKEN_VAI_TIME * 60L),

    // 登录用户基本信息 存储5min
    WX_USERINFO("wx_userinfo", 5 * 60L),

    // 微信小程序session_key
    WX_SESSION_KEY("wx_session_key", CommonConstants.WX_SESSION_KEY_VAI_TIME * 60L),

    // 微信用户uid
    WX_UID("wx_uid", CommonConstants.WX_LOGIN_TOKEN_VAI_TIME * 60L);

    // 存入redis的key前缀
    private final String prefix;

    // 存入redis的数据存活时间
    private final Long time;

    UaaRedisKeys(String prefix, Long time) {
        this.prefix = prefix;
        this.time = time;
    }

    public String join(String... values) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(this.prefix);
        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }

}
