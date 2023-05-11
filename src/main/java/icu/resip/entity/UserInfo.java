package icu.resip.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author Peng
 * @Date 2022/1/28 16:14
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class UserInfo {

    private Long id;//用户唯一id

    private String username;

    private String nickName;

    private boolean gender;

    private String avatarUrl;//头像图片路径

    private String phone;

    private Integer auth;

}
