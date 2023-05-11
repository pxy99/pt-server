package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.constants.CommonConstants;
import icu.resip.domain.address.Address;
import icu.resip.domain.address.Area;
import icu.resip.domain.address.AreaAddress;
import icu.resip.entity.AddressVo;
import icu.resip.result.Result;
import icu.resip.service.AddressService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/3/31
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    // 查询收货区域
    @GetMapping("/area")
    public Result<List<Area>> getArea() {
        // 查询收货区域
        List<Area> areas = addressService.listArea();
        return Result.success(areas);
    }

    // 查询收货区域下的具体地址
    @GetMapping("/name/{id}")
    public Result<List<AreaAddress>> getAreaAddressName(@PathVariable("id") Long id) {
        // 查询收货区域下的具体地址
        List<AreaAddress> areaAddresses = addressService.listAreaAddress(id);
        return Result.success(areaAddresses);
    }

    // 添加收获地址
    @CheckToken
    @PostMapping("/add-receive")
    public Result<Address> addReceive(@RequestBody Address address, HttpServletRequest request) {
        // 校验参数是否为空
        AssertUtil.isParamsNull(address.getName(), address.getPhone(), address.getAreaId(), address.getAreaAddressId(), address.getAddress(), address.isDefaulted());

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 添加收获地址
        address = addressService.addReceive(token, address);

        return Result.success(address);
    }

    // 修改收货地址
    @CheckToken
    @PutMapping("/edit-receive")
    public Result<Object> editReceive(@RequestBody Address address, HttpServletRequest request) {
        // 校验参数是否为空
        AssertUtil.isParamsNull(address.getId(), address.getName(), address.getPhone(), address.getAreaId(), address.getAreaAddressId(), address.getAddress(), address.isDefaulted());

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 编辑收货地址
        addressService.editReceive(token, address);

        return Result.success(null);
    }

    // 删除收货地址
    @CheckToken
    @DeleteMapping("/receive/{id}")
    public Result<Object> delReceive(@PathVariable("id") Long id, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(id);

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 删除收货地址
        addressService.delReceive(token, id);

        return Result.success(null);
    }

    // 指定地址id查询地址信息
    @CheckToken
    @GetMapping("/receive/{id}")
    public Result<Address> getReceive(@PathVariable("id") Long id, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(id);

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 指定地址id查询地址信息
        Address address = addressService.getReceive(token, id);

        return Result.success(address);
    }

    // 查询此用户所有收货地址
    @CheckToken
    @GetMapping("/receives")
    public Result<List<AddressVo>> listReceives(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 查询此用户所有收货地址
        List<AddressVo> addressList = addressService.listReceive(token);

        return Result.success(addressList);
    }

    // 查询此用户默认收货地址
    @CheckToken
    @GetMapping("/default")
    public Result<AddressVo> defaultR(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 查询此用户默认收货地址
        AddressVo addressVo = addressService.defaultR(token);

        return Result.success(addressVo);
    }

}
