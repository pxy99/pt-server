package icu.resip.captcha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * AJ-Captcha验证码配置
 * @Author Peng
 * @Date 2021/11/18 11:33
 * @Version 1.0
 */
@PropertySource("classpath:ajcaptcha.properties")
@Configuration
public class CaptchaConfig {
}
