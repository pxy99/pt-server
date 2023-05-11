package icu.resip.web.interceptor;

import com.alibaba.fastjson.JSON;
import icu.resip.annotation.CheckToken;
import icu.resip.constants.CommonConstants;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.result.Result;
import icu.resip.utils.AssertUtil;
import icu.resip.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @Author Peng
 * @Date 2022/1/28 18:55
 * @Version 1.0
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果是访问动态资源，拦截，否则放行
        if (handler instanceof HandlerMethod) {
            //拿到Controller中方法上的注解信息
            HandlerMethod method = (HandlerMethod) handler;
            CheckToken annotation = method.getMethodAnnotation(CheckToken.class);

            //判断方法上是否贴了注解，如果没贴，直接放行，如果贴了则继续执行
            if (annotation != null) {
                //获取从前端传过来的token
                String token = request.getHeader(CommonConstants.TOKEN_NAME);
                //校验参数是否为空
                AssertUtil.isParamsNull(token);
                //获取redis
                String openid = userRedisService.getWxOpenid(token);

                //符合token的正则
                String regex = "^[0-9a-z]{32}$";

                //通过tok从redis中获取openid，如果获取不到，说明token不合法，拦截
                if (!Pattern.matches(regex, token) || StringUtils.isEmptyOrNull(openid)) {
                    String jsonString = JSON.toJSONString(Result.error(CommonCodeMsg.ILLEGAL_TOKEN));
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(jsonString);
                    return false;
                }

                //token合法和openid有效的情况下，重新设置redis中token有效期30min和sessionKey2h
                userRedisService.reSetExpireTime(token, openid);
            }
        }
        return true;
    }
}
