package icu.resip.captcha.service.impl;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import icu.resip.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Peng
 * @Date 2021/11/18 13:02
 * @Version 1.0
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private com.anji.captcha.service.CaptchaService captchaService;

    @Override
    public boolean checkCaptchaAgain(String captchaVerification) {
        //对Captcha验证码做二次校验
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        ResponseModel response = captchaService.verification(captchaVO);
        //对不同的状态返回不同的结果
        /*
            repCode  0000  无异常，代表成功
            repCode  9999  服务器内部异常
            repCode  0011  参数不能为空
            repCode  6110  验证码已失效，请重新获取
            repCode  6111  验证失败
            repCode  6112  获取验证码失败,请联系管理员
         */
        //校验失败
        return response.isSuccess();
    }

}
