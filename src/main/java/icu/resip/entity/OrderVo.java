package icu.resip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Peng
 * @Date 2022/4/17
 */
@Getter
@Setter
public class OrderVo {

    private Long id;

    private String orderNo;

    private Long uid;

    private Long receiveUid;

    private String receivePhone;

    private String pickAddress;

    private String receiveAddress;

    private int amount;

    private String money;

    private String thingType;

    private String receiveCode;

    /**
     * 订单状态，0-待接单，1-已接单，2-配送中，3-自己下的单已完成，4-自己送得单已完成
     */
    private Integer status;

    /**
     * 订单过期时间，单位s
     */
    private long expireTime;

    public void setExpireTime(Date expireTime) {
        // 计算订单剩余时间
        Date now = new Date();
        this.expireTime = (expireTime.getTime() - now.getTime()) / 1000;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

}
