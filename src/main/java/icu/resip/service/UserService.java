package icu.resip.service;

import icu.resip.entity.UserInfo;
import icu.resip.qo.UserInfoQo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Peng
 * @Date 2022/1/28 15:37
 * @Version 1.0
 */
public interface UserService {

    /**
     * 请求微信服务器获取openid并存储session_key到redis中
     * @param code
     * @return
     * @throws IOException
     */
    String getOpenId(String code) throws IOException;

    /**
     * 使用UUID生成token令牌
     * @param openid
     * @return
     */
    String createToken(String openid);

    /**
     * 判断wx_openid是否存在库中
     * @param token
     * @param openid
     * @return
     */
    UserInfo isLoginOrRegister(String token, String openid);

    /**
     * 使用sessionKey和iv解密encryptedData获得用户信息并保存到数据库中
     * @param userInfoQo
     * @return
     */
    UserInfo saveUserInfo(UserInfoQo userInfoQo);

    /**
     * 修改昵称
     * @param token 身份令牌
     * @param newName 新昵称
     */
    void changeName(String token, String newName);

    /**
     * 上传图片修改头像
     * @param file 新上传的图片
     * @param token 身份令牌
     */
    String editAvatar(MultipartFile file, String token) throws IOException;

    /**
     * 上传图片到本地
     * @param file 图片
     * @param token 身份令牌
     * @return 图片的外网地址 http://192.168.1.6:7000/images/2/a.jpg
     * @throws IOException
     */
    String uploadLocal(MultipartFile file, String token) throws IOException;

    /**
     * 计算已注册天数
     * @param token
     * @return
     */
    Long getUsedDay(String token);

    /**
     * 从redis中获取用户信息
     * @param token
     * @return
     */
    UserInfo getUserInfo(String token);

    /**
     * 获取到用户手机号，存储到库中
     * @return 手机号
     */
    String savePhone(UserInfoQo userInfoQo);
}
