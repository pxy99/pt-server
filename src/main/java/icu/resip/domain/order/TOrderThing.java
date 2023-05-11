package icu.resip.domain.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Peng
 * @Date 2022/4/15
 */
@Getter
@Setter
public class TOrderThing {

    private Long id;

    private Long takeOrderId;

    private String storeName;

    private String desc;

}
