package icu.resip.web.interceptor;

import com.alibaba.fastjson.JSON;
import icu.resip.annotation.CheckToken;
import icu.resip.annotation.RequiredPermission;
import icu.resip.constants.CommonConstants;
import icu.resip.domain.uaa.Permission;
import icu.resip.domain.uaa.User;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.result.Result;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author Peng
 * @Date 2021/3/11 18:32
 * @Version 1.0
 */
@Component
public class CheckPermissionInterceptor implements HandlerInterceptor {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是访问动态资源，拦截，否则放行
        if (handler instanceof HandlerMethod) {
            //拿到Controller中方法上的注解信息
            HandlerMethod method = (HandlerMethod) handler;
            CheckToken annotationC = method.getMethodAnnotation(CheckToken.class);
            // 判断方法上是否贴了注解，如果没贴，直接放行，如果贴了则继续执行
            if (annotationC != null) {
                //获取从前端传过来的token
                String token = request.getHeader(CommonConstants.TOKEN_NAME);
                //校验参数是否为空
                AssertUtil.isParamsNull(token);
                // 拿到redis中登录用户的信息
                User user = userRedisService.getUser(token);

                // 判断是否为认证用户，如果是，则直接放行，不是则继续执行
                if (user.getAuth() != CommonConstants.AUTH_ED) {
                    // 拿到Controller中方法上的注解信息
                    RequiredPermission annotationP = method.getMethodAnnotation(RequiredPermission.class);

                    // 判断方法上是否贴了注解，如果没贴，直接放行，如果贴了则继续执行
                    if (annotationP != null) {
                        // 判断方法上注解的权限表达式是否与登录用户所拥有的权限表达式匹配，如果匹配，直接放行，如果不匹配，继续执行
                        List<Permission> permissions = user.getPermissions();
                        for (Permission permission : permissions) {
                            if (permission.getExpression().equals(annotationP.expression())) {
                                return true;
                            }
                        }
                        // 响应权限不足的信息
                        String jsonString = JSON.toJSONString(Result.error(CommonCodeMsg.ILLEGAL_OPERATION));
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write(jsonString);
                        return false;
                    }
                }
            }

        }
        return true;
    }
}
