package icu.resip.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import icu.resip.constants.CommonConstants;
import icu.resip.domain.uaa.Open;
import icu.resip.domain.uaa.Permission;
import icu.resip.domain.uaa.User;
import icu.resip.entity.UserInfo;
import icu.resip.exception.LogicException;
import icu.resip.mapper.UserMapper;
import icu.resip.pay.properties.WxPayProperties;
import icu.resip.pay.util.HttpUtils;
import icu.resip.qo.UserInfoQo;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.UserService;
import icu.resip.utils.AESUtil;
import icu.resip.utils.ImageUtils;
import icu.resip.utils.RequestUtil;
import icu.resip.utils.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Peng
 * @Date 2022/1/28 15:43
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String getOpenId(String code) throws IOException {
        // 微信小程序相关配置
        WxPayProperties payProperties = new WxPayProperties();

        //拼接url
        String url = "https://api.weixin.qq.com/sns/jscode2session?";
        //封装请求参数
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("appid", payProperties.getAppid());
        parameterMap.put("secret", payProperties.getSecret());
        parameterMap.put("js_code", code);
        parameterMap.put("grant_type", "authorization_code");

        //获取wx_openid
        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .data(parameterMap)
                .execute();
        String body = response.body();
        JSONObject jsonObject = JSON.parseObject(body);
        Object openid = jsonObject.get("openid");
        if (openid == null) {
            throw new LogicException(CommonCodeMsg.GET_OPENID_ERROR);
        }

        //将session_key存入redis中
        String session_key = jsonObject.get("session_key").toString();
        userRedisService.saveWxSessionKey(session_key, openid.toString());

        return openid.toString();
    }

    @Override
    public String createToken(String openid) {
        //生成token
        String token = IdUtil.simpleUUID();
        //将token和openid存入redis中，设置有效期7d，token为key，openid为value
        userRedisService.saveWxOpenid(token, openid);
        return token;
    }

    @Transactional
    @Override
    public UserInfo isLoginOrRegister(String token, String openid) {
        //判断openid是否在数据库中存在，存在说明是登录，不存在说明是注册
        Open open = userMapper.getOpenByWxOpenId(openid);

        //获取用户公网ip地址
        String ip = RequestUtil.getIPAddress();
        Date date = new Date();
        User user = new User();

        //定义一个对象，用来接收返回给前端的用户信息
        UserInfo userInfo = null;
        Long uid;
        if (open == null) {
            // 不存在，注册用户
            user.setLoginIp(ip);
            user.setLoginTime(date);
            user.setCreateTime(date);
            // 生成一个用户名
            user.setUsername(StringUtils.generateUsername());
            // 插入一条用户数据到user表中
            userMapper.insertUser(user);

            // 保存openid到open表中
            uid = user.getId();
            open = new Open();
            open.setUid(uid);
            open.setWxOpenid(openid);
            userMapper.insertOpen(open);

            // 给新注册用户分配未认证角色
            Long roleId = userMapper.selectRoleId(CommonConstants.UNAUTH);
            userMapper.insertUserRole(uid, roleId);
        } else {
            // 登录，更改登录时间和用户ip
            uid = userMapper.selectUserIdByOpenid(openid);
            user.setLoginIp(ip);
            user.setLoginTime(date);
            user.setId(uid);
            userMapper.updateUserInfo(user);

            //查询用户数据返回给前端显示
            userInfo = userMapper.selectUserInfo(uid);
        }
        //无论是注册还是登录，都将uid存到redis中
        userRedisService.saveWxUid(token, uid);
        // 存储用户信息和权限信息到redis中，后面每次请求都会校验用户是否有权限
        List<Permission> permissions = userMapper.listPermission(uid);
        // user = userMapper.selectUser(uid);
        user.setPermissions(permissions);
        userRedisService.saveUser(token, user);

        //返回此状态给小程序前端，前端判断是注册还是登录
        //如果是注册，继续拉取getUserProfile接口获取用户信息
        //如果是登录，不再请求getUserProfile接口获取用户信息
        return userInfo;
    }

    @Transactional
    @Override
    public UserInfo saveUserInfo(UserInfoQo userInfoQo) {
        //从redis中拿到session_key
        String openid = userRedisService.getWxOpenid(userInfoQo.getToken());
        String sessionKey = userRedisService.getWxSessionKey(openid);

        //解密获得用户信息
        User user = new User();
        try {
            byte[] bytes = AESUtil.decrypt(
                    Base64.decode(userInfoQo.getEncryptedData()),
                    Base64.decode(sessionKey),
                    Base64.decode(userInfoQo.getIv())
            );
            assert bytes != null;
            JSONObject jsonUserinfo = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
            //封装数据到user中
            user.setNickName(jsonUserinfo.get("nickName").toString());
            String genderStr = jsonUserinfo.get("gender").toString();
            user.setGender(genderStr.equals("1"));
            user.setAvatarUrl(jsonUserinfo.get("avatarUrl").toString());
        } catch (Exception e) {
            throw new LogicException(CommonCodeMsg.ERROR_ENCRYPTED_DATA);
        }

        //保存user到数据库中
        //通过token获取redis中保存的uid
        Long uid = userRedisService.getWxUid(userInfoQo.getToken());
        user.setId(uid);
        //更改部分用户信息
        userMapper.updateUser(user);
        //返回用户信息
        return userMapper.selectUserInfo(uid);
    }

    @Override
    public void changeName(String token, String newName) {
        // 获取自己的uid
        Long uid = userRedisService.getWxUid(token);
        // 修改自己的昵称
        userMapper.updateNickName(uid, newName);
    }

    @Override
    public String editAvatar(MultipartFile file, String token) throws IOException {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 上传图片到本地获取图片的外网地址
        String path = uploadLocal(file, token);

        // 替换掉user表中头像地址
        User user = new User();
        user.setId(uid);
        user.setAvatarUrl(path);
        int row = userMapper.updateAvatarUrl(user);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.EDIT_AVATAR_ERROR);
        }
        return path;
    }

    @Override
    public String uploadLocal(MultipartFile file, String token) throws IOException {
        // 检查是否是图片
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null || image.getWidth(null) <= 0 || image.getHeight(null) <= 0) {
            throw new LogicException(CommonCodeMsg.FILE_ONLY_IMG);
        }

        // 检查文件大小
        if (file.getSize() > 10485760) {//图片大于10M
            throw new LogicException(CommonCodeMsg.FILE_TOO_LARGE);
        }

        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 拼接要上传图片到本地的路径, eg: /home/Peng/pic/2
        String localPath = CommonConstants.PICTURE_DIRECTORY + uid;

        // 上传图片到本地，得到图片名称，eg: /a.jpg
        String filename = ImageUtils.uploadImage(file, localPath, true);

        // 拼接外网访问图片的相对路径,存储到数据库中, eg: http://192.168.1.6:7000/images/2/a.jpg
        return CommonConstants.PICTURE_URL + uid + filename;
    }

    @Override
    public Long getUsedDay(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询用户注册时间
        User user = userMapper.selectCreateTime(uid);
        Date createTime = user.getCreateTime();
        Date now = new Date();

        return DateUtil.between(createTime, now, DateUnit.DAY) + 1;
    }

    @Override
    public UserInfo getUserInfo(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 从redis获取用户信息
        UserInfo userInfo = userRedisService.getUserInfo(token);

        // 看一下有没有，如果没有再去mysql中查询
        if (userInfo == null || userInfo.getId() == null) {
            userInfo = userMapper.selectUserInfo(uid);
            // 再存储到redis中
            userRedisService.saveUserInfo(token, userInfo);

            return userInfo;
        }

        // 如果有，直接返回
        return userInfo;
    }

    @Override
    public String savePhone(UserInfoQo userInfoQo) {
        // 获取openid
        String openid = userRedisService.getWxOpenid(userInfoQo.getToken());

        // 获取session_key
        String sessionKey = userRedisService.getWxSessionKey(openid);

        // 解密获取手机号
        String phone;
        try {
            byte[] bytes = AESUtil.decrypt(
                    Base64.decode(userInfoQo.getEncryptedData()),
                    Base64.decode(sessionKey),
                    Base64.decode(userInfoQo.getIv())
            );
            assert bytes != null;
            JSONObject json = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8));
            phone = json.get("phoneNumber").toString();
        } catch (Exception e) {
            throw new LogicException(CommonCodeMsg.ERROR_ENCRYPTED_DATA);
        }

        // 保存手机号到user表
        Long uid = userRedisService.getWxUid(userInfoQo.getToken());
        int raw = userMapper.updatePhone(phone, uid);
        if (raw == 0) {
            throw new LogicException(CommonCodeMsg.GET_PHONE_ERROR);
        }

        return phone;
    }

}
