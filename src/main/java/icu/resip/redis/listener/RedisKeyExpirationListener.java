package icu.resip.redis.listener;

import icu.resip.constants.OrderConstants;
import icu.resip.domain.order.TOrder;
import icu.resip.mapper.TOrderMapper;
import icu.resip.redis.enums.OrderRedisKeys;
import icu.resip.redis.service.OrderRedisService;
import icu.resip.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 监听redis中过期数据
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
 
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    private TOrderMapper orderMapper;

    @Autowired
    public void setOrderMapper(TOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    private OrderRedisService orderRedisService;

    @Autowired
    public void setOrderRedisService(OrderRedisService orderRedisService) {
        this.orderRedisService = orderRedisService;
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message 过期数据
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取失效的key
        String expiredKey = message.toString();

        // 判断是否是需要处理过期时间到了就取消订单的key
        if (!StringUtils.isEmptyOrNull(expiredKey) && expiredKey.startsWith(OrderRedisKeys.EXPIRE_TAKE_ORDER_CANCEL.getPrefix())) {
            log.info("---------------------失效的key-{}--------------------", expiredKey);
            // 取出key中的订单编号
            String orderNo = expiredKey.replace(OrderRedisKeys.EXPIRE_TAKE_ORDER_CANCEL.getPrefix() + ":", "");

            // 查询订单信息
            TOrder order = orderMapper.selectByOrderNo(orderNo);

            // 如果订单状态还是待接单，更改订单状态为已取消
            if (order != null && order.getStatus() == OrderConstants.TO_BE_ORDER) {
                order.setCancelTime(new Date());
                order.setStatus(OrderConstants.CANCELED);
                orderMapper.updateStatus(order);
            }
            // 删除redis中key
            orderRedisService.removeTOrder(orderNo);
        }
    }
}