package icu.resip.mapper;

import icu.resip.domain.order.TOrder;
import icu.resip.domain.order.TOrderThing;
import icu.resip.entity.OrderDetail;
import icu.resip.entity.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/14
 */
@Repository
public interface TOrderMapper {

    /**
     * 根据order_no字段查询take_order表信息
     * @param orderNo
     * @return
     */
    TOrder selectByOrderNo(String orderNo);

    /**
     * 更改take_order表中status字段值
     */
    void updateStatus(TOrder tOrder);

    /**
     * 向take_order表插入一条数据
     * @param order
     */
    int insertOrder(TOrder order);

    /**
     * 向take_order_thing表插入一条数据
     * @param orderThing
     */
    int insertTakeOrderThing(TOrderThing orderThing);

    /**
     * 根据条件查询take_order表数据
     * @return
     */
    List<OrderVo> listByStatus(String where);

    /**
     * 根据订单id查询订单详情
     * @param id 订单id
     * @return
     */
    OrderDetail listDetailByStatus(Long id);

    /**
     * 接单更改take_order表中值
     * @param order
     * @return
     */
    int receiveOrder(TOrder order);

    /**
     * 根据uid查询take_order表数据
     * @param uid
     * @return
     */
    TOrder selectByUid(Long id, Long uid);

    /**
     * 取消订单更改take_order表中值
     * @param order
     * @return
     */
    int cancelOrder(TOrder order);

    /**
     * 确认送达更改take_order表中值
     * @param order
     */
    int confirmOrder(TOrder order);
}
