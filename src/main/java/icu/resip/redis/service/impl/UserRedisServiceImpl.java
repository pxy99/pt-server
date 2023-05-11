package icu.resip.redis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import icu.resip.domain.uaa.User;
import icu.resip.entity.UserInfo;
import icu.resip.exception.LogicException;
import icu.resip.redis.enums.UaaRedisKeys;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Peng
 * @Date 2022/1/28 15:55
 * @Version 1.0
 */
@Component
public class UserRedisServiceImpl implements UserRedisService {

    private StringRedisTemplate template;

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void saveWxSessionKey(String sessionKey, String openid) {
        template.opsForValue()
                .set(UaaRedisKeys.WX_SESSION_KEY.join(openid), sessionKey, UaaRedisKeys.WX_SESSION_KEY.getTime(), TimeUnit.SECONDS);
    }

    @Override
    public void saveWxOpenid(String token, String openid) {
        template.opsForValue()
                .set(UaaRedisKeys.WX_LOGIN_TOKEN.join(token), openid, UaaRedisKeys.WX_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
    }

    @Override
    public String getWxSessionKey(String openid) {
        return template.opsForValue().get(UaaRedisKeys.WX_SESSION_KEY.join(openid));
    }

    @Override
    public String getWxOpenid(String token) {
        return template.opsForValue().get(UaaRedisKeys.WX_LOGIN_TOKEN.join(token));
    }

    @Override
    public void reSetExpireTime(String token, String openid) {
        //重新设置token和sessionKey过期时间
        template.expire(UaaRedisKeys.WX_LOGIN_TOKEN.join(token), UaaRedisKeys.WX_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
        template.expire(UaaRedisKeys.WX_SESSION_KEY.join(openid), UaaRedisKeys.WX_SESSION_KEY.getTime(), TimeUnit.SECONDS);
    }

    @Override
    public void saveWxUid(String token, Long uid) {
        template.opsForValue().set(UaaRedisKeys.WX_UID.join(token), uid.toString());
    }

    @Override
    public Long getWxUid(String token) {
        String uidStr = template.opsForValue().get(UaaRedisKeys.WX_UID.join(token));
        return uidStr != null ? Long.valueOf(uidStr) : null;
    }

    @Override
    public void deleteRedisByToken(String token) {
        //获取openid
        String openid = this.getWxOpenid(token);
        //清除redis用户数据
        Set<String> keys = new HashSet<>();
        keys.add(UaaRedisKeys.WX_UID.join(token));
        keys.add(UaaRedisKeys.WX_SESSION_KEY.join(openid));
        keys.add(UaaRedisKeys.WX_LOGIN_TOKEN.join(token));
        Long deleteCount = template.delete(keys);
        if (deleteCount == null || deleteCount == 0) {
            throw new LogicException(CommonCodeMsg.LOGOUT_ERROR);
        }
    }

    @Override
    public void saveUser(String token, User user) {
        // 将user序列化为json字符串
        String userJson = JSON.toJSONString(user);
        // 存储到redis中
        template.opsForValue().set(UaaRedisKeys.WX_USER.join(token), userJson, UaaRedisKeys.WX_USER.getTime(), TimeUnit.SECONDS);
    }

    @Override
    public User getUser(String token) {
        String userJson = template.opsForValue().get(UaaRedisKeys.WX_USER.join(token));
        if (!StringUtils.isEmptyOrNull(userJson)) {
            return JSONObject.parseObject(userJson, User.class);
        }
        return null;
    }

    @Override
    public void saveUserInfo(String token, UserInfo userInfo) {
        // 将user序列化为json字符串
        String userJson = JSON.toJSONString(userInfo);
        // 存储到redis中
        template.opsForValue().set(UaaRedisKeys.WX_USERINFO.join(token), userJson, UaaRedisKeys.WX_USERINFO.getTime(), TimeUnit.SECONDS);
    }

    @Override
    public UserInfo getUserInfo(String token) {
        String userJson = template.opsForValue().get(UaaRedisKeys.WX_USERINFO.join(token));
        if (!StringUtils.isEmptyOrNull(userJson)) {
            return JSONObject.parseObject(userJson, UserInfo.class);
        }
        return null;
    }
}
