package icu.resip.service;

import icu.resip.entity.OrderDetail;
import icu.resip.entity.OrderVo;
import icu.resip.qo.ReceiveQo;
import icu.resip.qo.TOrderQo;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/14
 */
public interface TOrderService {

    /**
     * 创建订单
     * @param orderQo 订单数据
     * @param token 用户令牌
     * @return 订单id
     */
    Long place(TOrderQo orderQo, String token);

    /**
     * 根据订单状态查询订单数据
     * @param status
     * @param token
     * @return
     */
    List<OrderVo> listByStatus(Integer status, String token);

    /**
     * 根据不同的订单状态查询订单详情
     * @param token
     * @return
     */
    OrderDetail listDetailByStatus(Long id, String token);

    /**
     * 跑腿员接单
     * @param id 订单id
     * @param token 跑腿员身份令牌
     */
    void receive(Long id, String token);

    /**
     * 取消订单
     * @param id
     * @param token
     */
    void cancel(Long id, String token);

    /**
     * 给订单续约
     * @param receiveQo
     * @param token
     */
    void renewal(ReceiveQo receiveQo, String token);

    /**
     * 确认送达
     * @param token
     */
    void confirm(ReceiveQo receiveQo, String token);
}
