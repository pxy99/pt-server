package icu.resip.domain.uaa;

import com.fasterxml.jackson.annotation.JsonFormat;
import icu.resip.constants.CommonConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/3/29
 */
@Getter
@Setter
@ToString
public class User implements Serializable {

    private Long id;

    private String username;

    private String nickName;

    private boolean gender;

    private String avatarUrl;

    private String phone;

    private BigDecimal balance = new BigDecimal("0");

    private String passwd;

    private String loginIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private int auth = CommonConstants.UN_AUTH;

    private boolean exist = true;

    List<Permission> permissions;

}
