package icu.resip.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装微信小程序保存用户信息的数据
 * @Author Peng
 * @Date 2022/1/28 19:05
 * @Version 1.0
 */
@Getter
@Setter
public class UserInfoQo {

    private String encryptedData;//用户加密数据

    private String iv;//算法向量

    private String token;//登录令牌

}
