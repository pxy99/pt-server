package icu.resip.domain.address;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Peng
 * @Date: 2022/3/31
 */
@Getter
@Setter
public class Address {

    private Long id;

    private Long uid;

    private String name;

    private String phone;

    private Long areaId;

    private Long areaAddressId;

    private String address;

    private boolean defaulted;

}
