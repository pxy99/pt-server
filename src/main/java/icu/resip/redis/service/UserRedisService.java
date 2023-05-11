package icu.resip.redis.service;

import icu.resip.domain.uaa.User;
import icu.resip.entity.UserInfo;

/**
 * @Author Peng
 * @Date 2022/1/28 15:53
 * @Version 1.0
 */
public interface UserRedisService {

    /**
     * 保存微信session_key到redis中，有效时间24h
     * @param sessionKey
     * @param openid
     */
    void saveWxSessionKey(String sessionKey, String openid);

    /**
     * 保存微信小程序的openid到redis中，有效期2h
     * @param token
     * @param openid
     */
    void saveWxOpenid(String token, String openid);

    /**
     * 从redis中获取保存的微信session_key
     * @param openid
     * @return
     */
    String getWxSessionKey(String openid);

    /**
     * 从redis中获取微信小程序的openid
     * @param token
     * @return
     */
    String getWxOpenid(String token);

    /**
     * 重新设置redis中token有效期30min和sessionKey2h
     * @param token
     * @param openid
     */
    void reSetExpireTime(String token, String openid);

    /**
     * 保存用户uid，下次直接从redis中获取
     * @param token
     * @param uid
     */
    void saveWxUid(String token, Long uid);

    /**
     * 通过token到redis获取uid
     * @param token
     * @return
     */
    Long getWxUid(String token);

    /**
     * 清除redis中的数据
     * wx_uid:token--uid/wx_session_key:openid--sessionKey/wx_login_token:token--openid
     * @param token
     */
    void deleteRedisByToken(String token);

    /**
     * 存储user信息和权限信息
     * @param token
     * @param user
     */
    void saveUser(String token, User user);

    /**
     * 获取redis中存储的user信息
     * @param token
     * @return
     */
    User getUser(String token);

    /**
     * 存储userinfo信息
     * @param token
     * @param userInfo
     */
    void saveUserInfo(String token, UserInfo userInfo);

    /**
     * 获取redis中存储的userinfo信息
     * @param token
     * @return
     */
    UserInfo getUserInfo(String token);
}
