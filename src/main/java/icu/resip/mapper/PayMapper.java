package icu.resip.mapper;

import icu.resip.pay.domain.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author Peng
 * @Date 2022/4/10
 */
@Repository
public interface PayMapper {

    /**
     * 创建充值订单
     * @param order
     */
    void insert(Order order);

    /**
     * 根据订单编号查询recharge_order表信息
     * @param orderNo
     * @return
     */
    Order selectOne(String orderNo);

    /**
     * 修改recharge_order表订单状态，失败原因及支付时间
     * @param order
     */
    int updateOrder(Order order);
}
