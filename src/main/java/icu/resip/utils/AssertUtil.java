package icu.resip.utils;

import icu.resip.exception.LogicException;
import icu.resip.result.CommonCodeMsg;

/**
 * 断言，遇空值抛出自定义异常
 * @Author Peng
 * @Date 2021/11/3 22:03
 * @Version 1.0
 */
public class AssertUtil {

    private AssertUtil() {}

    /**
     * 批量判断参数是否为空，只要有一个为空就抛出异常
     * @param objects 参数
     */
    public static void isParamsNull(Object... objects) {
        if (objects == null || objects.length == 0) {
            throw new LogicException(CommonCodeMsg.NULL_PARAM);
        }

        for (Object object : objects) {
            if (object == null || StringUtils.isEmptyOrNull(object.toString())) {
                throw new LogicException(CommonCodeMsg.NULL_PARAM);
            }
        }
    }

}
