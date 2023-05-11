package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.constants.CommonConstants;
import icu.resip.exception.LogicException;
import icu.resip.result.CommonCodeMsg;
import icu.resip.result.Result;
import icu.resip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author Peng
 * @Date 2022/4/7
 */
@RestController
@RequestMapping("/api/pic")
public class PicController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // 上传图片
    @CheckToken
    @PostMapping("/upload")
    public Result<String> uploadLocal(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // 校验参数是否为空
        if (file == null) {
            throw new LogicException(CommonCodeMsg.NULL_PARAM);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 上传图片到本地
        try {
            String url = userService.uploadLocal(file, token);
            return Result.success(url);
        } catch (IOException e) {
            throw new LogicException(CommonCodeMsg.FILE_UPLOAD_ERROR);
        }
    }

}
