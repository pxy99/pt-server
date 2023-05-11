package icu.resip.domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 帮我取订单
 * @Author Peng
 * @Date 2022/4/13
 */
@Getter
@Setter
public class TOrder {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * user表id，下单用户
     */
    private Long uid;

    /**
     * user表id，接单用户
     */
    private Long receiveUid;

    /**
     * thing表id
     */
    private Long thingId;

    /**
     * 物品总数量
     */
    private int amount;

    /**
     * 是否送上楼，true-是
     */
    private boolean upstairs;

    /**
     * 物品重量
     */
    private String weight;

    /**
     * 备注
     */
    private String ps;

    /**
     * 取货地址的人名
     */
    private String pickName;

    /**
     * 取货地址的手机号
     */
    private String pickPhone;

    /**
     * 取货地址
     */
    private String pickAddress;

    /**
     * receive_address表id
     */
    private Long receiveId;

    /**
     * 收货地址
     */
    private String receiveAddress;

    /**
     * 订单过期时间，到点取消订单
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 订单总额
     */
    private BigDecimal money;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 收货码
     */
    private String receiveCode;

    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 接单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /**
     * 订单状态，0-待接单，1-已接单/配送中，2-已完成，3-已取消
     */
    private int status;

    /**
     * 完成订单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /**
     * 取消订单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;

}
