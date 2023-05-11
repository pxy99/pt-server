package icu.resip.web.advice;

import icu.resip.exception.LogicException;
import icu.resip.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用异常处理类
 *  ControllerAdvice  controller类功能增强注解, 动态代理controller类实现一些额外功能
 *
 *  请求进入controller映射方法之前做功能增强: 经典用法:日期格式化
 *  请求进入controller映射方法之后做功能增强: 经典用法:统一异常处理
 */
@ControllerAdvice
public class ExceptionAdvice {

    //捕获自定义异常
    @ExceptionHandler(LogicException.class)
    @ResponseBody
    public Result handleBusinessException(LogicException ex){
        return Result.error(ex.getCodeMsg());
    }

    //捕获系统异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleDefaultException(Exception ex){
        ex.printStackTrace();//在控制台打印错误消息.
        return Result.defaultError();
    }

}