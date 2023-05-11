package icu.resip.redis.service.impl;

import icu.resip.redis.enums.OrderRedisKeys;
import icu.resip.redis.service.OrderRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author Peng
 * @Date 2022/4/15
 */
@Service
public class OrderRedisServiceImpl implements OrderRedisService {

    private StringRedisTemplate template;

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void saveTOrder(String orderNo, Long expireTime) {
        template.opsForValue().set(OrderRedisKeys.EXPIRE_TAKE_ORDER_CANCEL.join(orderNo), orderNo, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void removeTOrder(String orderNo) {
        template.delete(OrderRedisKeys.EXPIRE_TAKE_ORDER_CANCEL.join(orderNo));
    }

    @Override
    public void reSetExpireTime(String orderNo, long time) {
        // 查询订单剩余有效时间
        Long expire = template.opsForValue().getOperations().getExpire(OrderRedisKeys.EXPIRE_TAKE_ORDER_CANCEL.join(orderNo), TimeUnit.SECONDS);
        if (expire != null) {
            // 重新设置订单有效时间
            template.opsForValue().set(OrderRedisKeys.EXPIRE_TAKE_ORDER_CANCEL.join(orderNo), orderNo, expire + time, TimeUnit.SECONDS);
        }
    }

}
