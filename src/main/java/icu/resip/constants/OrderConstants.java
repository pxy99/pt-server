package icu.resip.constants;

/**
 * @Author Peng
 * @Date 2022/4/15
 */
public class OrderConstants {

    private OrderConstants() {}

    /**
     * 待接单
     */
    public static final int TO_BE_ORDER = 0;

    /**
     * 已接单/配送中
     */
    public static final int ORDERED = 1;

    /**
     * 已完成
     */
    public static final int SUCCESS = 2;

    /**
     * 已取消
     */
    public static final int CANCELED = 3;

}
