package icu.resip.mapper;

import icu.resip.domain.address.Address;
import icu.resip.domain.address.Area;
import icu.resip.domain.address.AreaAddress;
import icu.resip.entity.AddressVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/3/31
 */
@Repository
public interface AddressMapper {

    /**
     * 在receive_address表中插入一条数据
     * @param address 地址信息
     * @return 插入数据条数
     */
    int insertR(Address address);

    /**
     * 更改receive_address表中address字段
     * @param address
     * @return
     */
    int updateR(Address address);

    /**
     * 删除receive_address表中指定一条数据
     * @param address
     * @return
     */
    int deleteR(Address address);

    /**
     * 根据uid查询receive_address表中所有数据
     * @param uid 用户唯一id
     * @return
     */
    List<AddressVo> listR(@Param("uid") Long uid);

    /**
     * 更改receive_address表中defaulted字段
     * @param address
     */
    void updateDefault(Address address);

    /**
     * 根据uid和defaulted查询表中数据
     * @param uid
     * @param defaulted
     * @return
     */
    Address selectAddress(@Param("uid") Long uid, @Param("defaulted") boolean defaulted);

    /**
     * 查询出area表中所有的数据
     * @return
     */
    List<Area> listArea();

    /**
     * 根据areaId查询出area_address表中数据
     * @param areaId
     * @return
     */
    List<AreaAddress> listAreaAddress(Long areaId);

    /**
     * 根据uid和defaulted查询表中数据
     * @param uid
     * @param defaulted
     * @return
     */
    AddressVo selectAddressDefault(@Param("uid") Long uid, @Param("defaulted") boolean defaulted);

    /**
     * 根据地址id查询receive_address表数据
     * @param id 主键id
     * @param uid 用户id
     * @return
     */
    Address findAddress(@Param("id") Long id, @Param("uid") Long uid);
}
