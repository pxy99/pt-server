package icu.resip.pay.util;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class Snowflake {

    //下面两个每个5位，加起来就是10位的工作机器id
    private final long workerId;    //工作ID  2进制5位  数值0-31
    private final long datacenterId;   //数据id 2进制5位  数值0-31
    //12位的序列号
    private long sequence;

    public Snowflake(long workerId, long datacenterId, long sequence) {
        // sanity check for workerId
        //最大值
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    //长度为5位
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;

    //上次时间戳，初始值为负数
    private long lastTimestamp = -1L;

    //下一个ID生成算法
    public synchronized long nextId() {
        long timestamp = timeGen();

        //获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        //获取当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；否则序列号赋值为0，从0开始。
        //序列号id长度
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            //序列号最大值
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        //将上次时间戳值刷新
        lastTimestamp = timestamp;

        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        //工作id需要左移的位数，12位
        //数据id需要左移位数 12+5=17位
        long datacenterIdShift = sequenceBits + workerIdBits;
        //初始时间戳
        long twepoch = 1288834974657L;
        //时间戳需要左移位数 12+5+5=22位
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << sequenceBits) |
                sequence;
    }

    /**
     * 获取时间戳，并与上次时间戳比较
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取系统时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 这里简单实现，通过随机数生成工作ID、数据ID
     * 不生成重复id要通过datacenterId和workerId来做区分
     */
    private static class SingletonClassInstance {
        static Random random = new Random();
        private static final Snowflake instance = new Snowflake(random.nextInt(31), random.nextInt(31), 1);
    }

    /**
     * 单例调用入口
     */
    public static Snowflake getInstance() {
        return SingletonClassInstance.instance;
    }

    //---------------测试---------------
    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            System.out.println(Snowflake.getInstance().nextId());
            //System.out.println(IdUtil.getSnowflake().nextId());
        }
    }
}