package icu.resip.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对Controller中请求映射方法作标记，如果贴了该注解，对其拦截，否则放行
 * @Author Peng
 * @Date 2022/1/28 18:54
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckToken {
}
