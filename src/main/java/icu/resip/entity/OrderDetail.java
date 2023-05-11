package icu.resip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/17
 */
@Getter
@Setter
public class OrderDetail {

    private Long id;

    private String orderNo;

    private Long uid;

    private String nickName;

    private String avatarUrl;

    private String name;

    private String phone;

    private Long receiveUid;

    private String receivePhone;

    private String pickAddress;

    private String receiveAddress;

    private int amount;

    private String weight;

    private boolean upstairs;

    private String money;

    private String thingType;

    private String receiveCode;

    private List<ThingVo> thingVoList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    private String payType;

    private String ps;

}
