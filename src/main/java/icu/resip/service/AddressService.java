package icu.resip.service;

import icu.resip.domain.address.Address;
import icu.resip.domain.address.Area;
import icu.resip.domain.address.AreaAddress;
import icu.resip.entity.AddressVo;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/3/31
 */
public interface AddressService {

    /**
     * 添加收货地址
     * @param token
     * @param address
     */
    Address addReceive(String token, Address address);

    /**
     * 编辑收货地址
     * @param token
     * @param address
     */
    void editReceive(String token, Address address);

    /**
     * 删除收货地址
     * @param token
     * @param id 收货地址唯一id
     */
    void delReceive(String token, Long id);

    /**
     * 查询所有收货地址
     * @param token
     * @return
     */
    List<AddressVo> listReceive(String token);

    /**
     * 查询此用户默认收货地址
     * @param token
     * @return
     */
    AddressVo defaultR(String token);

    /**
     * 查询所有的区域名称
     * @return
     */
    List<Area> listArea();

    /**
     * 查询收货区域下的具体地址
     * @param id
     * @return
     */
    List<AreaAddress> listAreaAddress(Long id);

    /**
     * 指定地址id查询地址信息
     * @param token
     * @param id 地址id
     * @return
     */
    Address getReceive(String token, Long id);
}
