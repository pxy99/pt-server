package icu.resip.redis.enums;

import lombok.Getter;

/**
 * @Author Peng
 * @Date 2022/4/15
 */
@Getter
public enum OrderRedisKeys {

    /**
     * 过期订单取消，单位秒
     */
    EXPIRE_TAKE_ORDER_CANCEL("expire_take_order_cancel", 1L);

    // 存入redis的key前缀
    private final String prefix;

    // 存入redis的数据存活时间
    private final Long time;

    OrderRedisKeys(String prefix, Long time) {
        this.prefix = prefix;
        this.time = time;
    }

    public String join(String... values) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(this.prefix);
        for (String value : values) {
            sb.append(":").append(value);
        }
        return sb.toString();
    }

}
