package icu.resip.mapper;

import icu.resip.domain.order.Store;
import icu.resip.domain.order.Thing;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/13
 */
@Repository
public interface ThingMapper {

    /**
     * 查询thing表所有数据
     * @return
     */
    List<Thing> listAll();

    /**
     * 根据tid查询store表中数据
     * @param tid thing表id
     * @return
     */
    List<Store> listStore(Long tid);
}
