package icu.resip.domain.uaa;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Peng
 * @Date 2022/1/28 14:25
 * @Version 1.0
 */
@Getter
@Setter
public class Open implements Serializable {

    private Long id;

    private Long uid;//user表主键id

    private String wxOpenid;//微信小程序用户唯一标识

}
