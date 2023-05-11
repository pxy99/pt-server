package icu.resip.service.impl;

import icu.resip.domain.address.Address;
import icu.resip.domain.address.Area;
import icu.resip.domain.address.AreaAddress;
import icu.resip.entity.AddressVo;
import icu.resip.exception.LogicException;
import icu.resip.mapper.AddressMapper;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/3/31
 */
@Service
public class AddressServiceImpl implements AddressService {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public Address addReceive(String token, Address address) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 如果用户需要设置默认地址
        if (address.isDefaulted()) {
            // 查询此用户是否已设置了默认地址
            Address add = addressMapper.selectAddress(uid, true);
            if (add != null && add.getId() != null) {
                // 如果已经设置了默认地址，将其默认设置取消
                add.setDefaulted(false);
                addressMapper.updateDefault(add);
            }
        }

        // 如果没有设置默认地址，将此条地址设置为默认地址
        // 在receive_address表中插入一条地址数据
        address.setUid(uid);
        int row = addressMapper.insertR(address);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.ADD_ADDRESS_ERROR);
        }

        return address;
    }

    @Override
    public void editReceive(String token, Address address) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 如果用户需要设置默认地址
        if (address.isDefaulted()) {
            // 查询此用户是否已设置了默认地址
            Address add = addressMapper.selectAddress(uid, true);
            if (add != null && add.getId() != null) {
                // 如果已经设置了默认地址，将其默认设置取消
                add.setDefaulted(false);
                addressMapper.updateDefault(add);
            }
        }

        // 修改mysql中数据
        address.setUid(uid);
        int row = addressMapper.updateR(address);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.EDIT_ADDRESS_ERROR);
        }
    }

    @Override
    public void delReceive(String token, Long id) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 删除mysql中收货地址信息
        Address address = new Address();
        address.setId(id);
        address.setUid(uid);
        int row = addressMapper.deleteR(address);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.DEL_ADDRESS_ERROR);
        }
    }

    @Override
    public List<AddressVo> listReceive(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询mysql中所有收货地址信息
        return addressMapper.listR(uid);
    }

    @Override
    public AddressVo defaultR(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询mysql中此用户的默认收货地址
        AddressVo defaultAdd = addressMapper.selectAddressDefault(uid, true);

        // 如果没有设置默认收货地址，取最先添加的收货地址
        if (defaultAdd == null) {
            List<AddressVo> addressList = addressMapper.listR(uid);
            if (addressList != null && addressList.size() != 0) {
                defaultAdd = addressList.get(0);
            }
        }

        return defaultAdd;
    }

    @Override
    public List<Area> listArea() {
        return addressMapper.listArea();
    }

    @Override
    public List<AreaAddress> listAreaAddress(Long id) {
        return addressMapper.listAreaAddress(id);
    }

    @Override
    public Address getReceive(String token, Long id) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询地址信息
        return addressMapper.findAddress(id, uid);
    }

}
