package icu.resip.captcha.service;

/**
 * @Author Peng
 * @Date 2021/11/18 12:52
 * @Version 1.0
 */
public interface CaptchaService {

    /**
     * 二次校验Captcha验证码
     * @param captchaVerification 二次校验所需参数
     */
    boolean checkCaptchaAgain(String captchaVerification);

}
