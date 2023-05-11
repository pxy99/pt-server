package icu.resip.utils;

import java.util.Random;

/**
 * @Author Peng
 * @Date 2022/1/28 14:30
 * @Version 1.0
 */
public class StringUtils {

    private StringUtils() {}

    /**
     * 判断字符串是否为空
     * @param str 字符串参数
     * @return 空为true
     */
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().equals("") || str.equals("null");
    }

    /**
     * 随机生成n位数字
     * @return 随机数字
     */
    public static String generateNum(int n) {
        //定义字符库
        String str = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //生成随机字符下标
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 生成一个唯一id作为用户名
     * @return 用户名
     */
    public static String generateUsername() {
        return "pt" + generateNum(12);
    }

    /**
     * 随机生成n位大写字符
     * @param n 字符位数
     * @return 随机字符
     */
    public static String generateCode(int n) {
        //定义字符库
        String str = "0123456789ABCDEFGHJKMNPQRSTUVWXY";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //生成随机字符下标
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

}
