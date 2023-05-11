package icu.resip.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Peng
 * @Date 2022/4/16
 */
@Getter
@Setter
public class AddressVo {

    private Long id;

    private Long uid;

    private String name;

    private String phone;

    private String address;

    private boolean defaulted;

}
