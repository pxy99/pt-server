package icu.resip.qo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/14
 */
@Getter
@Setter
public class TOrderQo {

    /**
     * 物品类型id
     */
    private Long thingId;

    /**
     * 店名及物品详情
     */
    private List<ThingQo> thingQoList;

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
     * 自动退单时间，单位s
     */
    private Long expireTime;

    /**
     * 订单总额
     */
    private String money;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 用户钱包密码
     */
    private String passwd;

}
