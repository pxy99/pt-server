package icu.resip.domain.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Peng
 * @Date 2022/4/13
 */
@Getter
@Setter
public class Thing implements Serializable {

    private Long id;

    private String type;

}
