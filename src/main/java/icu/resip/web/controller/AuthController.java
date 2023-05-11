package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.constants.CommonConstants;
import icu.resip.domain.uaa.AuthFile;
import icu.resip.result.Result;
import icu.resip.service.AuthService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 跑腿认证资源控制器
 * @Author: Peng
 * @Date: 2022/3/31
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    // 发起认证
    @CheckToken
    @PostMapping("/submit")
    public Result<Object> submit(@RequestBody AuthFile authFile, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(authFile.getSfzZ(), authFile.getSfzF(), authFile.getXszF(), authFile.getXszX(), authFile.getRlz());

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 提交审核
        authService.submit(authFile, token);

        return Result.success(null);
    }

}
