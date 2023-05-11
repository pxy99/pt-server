package icu.resip.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Peng
 * @Date 2022/4/16
 */
@Getter
@Setter
public class TaskQo {

    /**
     * 价格排序，true-升序
     */
    private Boolean price;

    /**
     * 时间排序，true-升序
     */
    private Boolean time;

    /**
     * 收货地址模糊查询
     */
    private String address;

    /**
     * 价格区间筛选，最低价
     */
    private Integer minPrice;

    /**
     * 价格区间筛选，最高价
     */
    private Integer maxPrice;

    /**
     * order by子句
     */
    private String orderBy;

}
