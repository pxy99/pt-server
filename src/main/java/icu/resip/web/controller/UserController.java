package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.annotation.RequiredPermission;
import icu.resip.constants.CommonConstants;
import icu.resip.entity.UserInfo;
import icu.resip.exception.LogicException;
import icu.resip.qo.UserInfoQo;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.result.Result;
import icu.resip.service.UserService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Peng
 * @Date 2022/1/28 15:31
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    // 微信一键登录
    @PostMapping("/login/{code}")
    public Result<Map<String, Object>> login(@PathVariable("code") String code) throws IOException {
        // 校验参数是否为空
        AssertUtil.isParamsNull(code);
        //使用code请求微信服务器获取openid和session_key，并存储session_key到redis中
        String openid = userService.getOpenId(code);

        //使用UUID生成一个令牌token，存入redis中
        //下次小程序请求必须携带该token，后台判断token是否存在并在有效期内
        //存在不过期说明用户已登陆，更改用户状态为1
        String token = userService.createToken(openid);

        //判断openid是否在数据库中存在，存在说明是登录，不存在说明是注册，需要在数据库中insert一条数据，填入openid
        //更改用户状态为1，表示已登录
        UserInfo userInfo = userService.isLoginOrRegister(token, openid);

        //定义一个flag，代表次操作是注册状态还是登录状态 false：登录，true：注册
        Map<String, Object> data = new HashMap<>();
        boolean isRegister = userInfo == null;
        data.put("isRegister", isRegister);
        if (!isRegister) {//登录操作时才返回，注册时userInfo=null，返回无意义
            data.put("userInfo", userInfo);
        }

        //返回token给前端
        data.put("token", token);

        return Result.success(data);
    }

    // 保存用户信息
    @CheckToken
    @PutMapping("/save-userinfo")
    public Result<UserInfo> saveUserinfo(@RequestBody UserInfoQo userInfoQo, HttpServletRequest request) {
        // 校验参数是否为空
        AssertUtil.isParamsNull(userInfoQo.getEncryptedData(), userInfoQo.getIv());
        //先从redis中拿到session_key
        //再使用session_key和iv解密encryptedData得到用户信息，并保存到数据库中
        //获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);
        userInfoQo.setToken(token);
        UserInfo userInfo = userService.saveUserInfo(userInfoQo);

        return Result.success(userInfo);
    }

    // 获取到用户手机号，存储
    @CheckToken
    @PutMapping("/phone")
    public Result<String> getPhone(@RequestBody UserInfoQo userInfoQo, HttpServletRequest request) {
        // 校验参数是否为空
        AssertUtil.isParamsNull(userInfoQo.getEncryptedData(), userInfoQo.getIv());

        //获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);
        userInfoQo.setToken(token);

        // 获取到用户的手机号，存储到库中
        String phone = userService.savePhone(userInfoQo);

        return Result.success(phone);
    }

    // 注销/退出登录
    @CheckToken
    @DeleteMapping("/logout")
    public Result<Object> logout(HttpServletRequest request) {
        //获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);
        //注销登录，就是清除redis中的数据
        //wx_uid:token--uid/wx_session_key:openid--sessionKey/wx_login_token:token--openid
        userRedisService.deleteRedisByToken(token);
        return Result.success(null);
    }

    // 修改昵称
    @RequiredPermission(name = "修改昵称", expression = "user:nickname-edit")
    @CheckToken
    @PutMapping("/change-name")
    public Result<String> changeName(@RequestBody Map<String, String> map, HttpServletRequest request) {
        // 拿到新昵称
        String newName = map.get("newName");
        // 校验参数是否为空
        AssertUtil.isParamsNull(map, newName);
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);
        // 修改昵称
        userService.changeName(token, newName);

        return Result.success(newName);
    }

    // 修改头像
    @RequiredPermission(name = "修改头像", expression = "user:avatar-edit")
    @CheckToken
    @PostMapping("/change-img")
    public Result<String> changeImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // 校验参数是否为空
        if (file == null) {
            throw new LogicException(CommonCodeMsg.NULL_PARAM);
        }

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 上传图片修改头像
        try {
            String path = userService.editAvatar(file, token);
            return Result.success(path);
        } catch (IOException e) {
            throw new LogicException(CommonCodeMsg.EDIT_AVATAR_ERROR);
        }
    }

    // 已注册天数
    @CheckToken
    @GetMapping("/used-day")
    public Result<Long> usedDay(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 计算已注册天数
        Long day = userService.getUsedDay(token);

        return Result.success(day);
    }

    // 获取用户信息
    @CheckToken
    @GetMapping("/info")
    public Result<UserInfo> getUserInfo(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 从redis中获取用户信息
        UserInfo userInfo = userService.getUserInfo(token);

        return Result.success(userInfo);
    }

}
