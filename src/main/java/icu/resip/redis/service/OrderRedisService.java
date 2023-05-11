package icu.resip.redis.service;

/**
 * @Author Peng
 * @Date 2022/4/15
 */
public interface OrderRedisService {

    /**
     * 在一定时间内保存订单到redis，时间到了，redis中key过期，会通知，执行订单取消计划
     * @param orderNo 订单编号
     * @param expireTime 订单过期时间
     */
    void saveTOrder(String orderNo, Long expireTime);

    /**
     * 移除redis中订单数据
     */
    void removeTOrder(String orderNo);

    /**
     * 更新订单过期时间
     * @param orderNo
     * @param time
     */
    void reSetExpireTime(String orderNo, long time);
}
