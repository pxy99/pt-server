package icu.resip.config;

import icu.resip.constants.CommonConstants;
import icu.resip.web.interceptor.CheckPermissionInterceptor;
import icu.resip.web.interceptor.CheckTokenInterceptor;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Peng
 * @Date 2022/1/28 19:00
 * @Version 1.0
 */
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

    private CheckTokenInterceptor checkTokenInterceptor;

    @Autowired
    public void setCheckTokenInterceptor(CheckTokenInterceptor checkTokenInterceptor) {
        this.checkTokenInterceptor = checkTokenInterceptor;
    }

    private CheckPermissionInterceptor checkPermissionInterceptor;

    @Autowired
    public void setCheckPermissionInterceptor(CheckPermissionInterceptor checkPermissionInterceptor) {
        this.checkPermissionInterceptor = checkPermissionInterceptor;
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加校验token是否合法的拦截器
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/**")
                //放行用户登录路径
                .excludePathPatterns("/api/user/login/**", "/api/permission/**");

        // 添加校验权限的拦截器
        registry.addInterceptor(checkPermissionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/user/login/**", "/api/user/save-userinfo", "/api/user/logout");
    }

    // 配置静态资源映射路径，映射服务器中存储图片文件夹，让外界能直接访问此文件夹里的图片
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置图片存储映射地址，使用户能访问到项目外部图片文件
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + CommonConstants.PICTURE_DIRECTORY);
    }

    /**
     * http重定向到https
     * @return
     */
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }
    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(7000);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(443);
        return connector;
    }

}
