package icu.resip.service;

import icu.resip.domain.order.Store;
import icu.resip.domain.order.Thing;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/13
 */
public interface ThingService {

    /**
     * 查询出所有的物品类型
     * @return
     */
    List<Thing> listType();

    /**
     * 根据物品id查询店名
     * @param tid 物品类型id
     * @return
     */
    List<Store> listStore(Long tid);
}
