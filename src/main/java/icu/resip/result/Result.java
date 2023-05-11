package icu.resip.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Peng
 * @Date 2022/1/28 14:32
 * @Version 1.0
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    public static final int SUCCESS_CODE = 200;//成功码

    public static final String SUCCESS_MESSAGE = "success";//成功信息

    public static final int ERROR_CODE = 500;//错误码

    public static final String ERROR_MESSAGE = "系统异常，请联系管理员";//错误信息

    private int code;

    private String msg;

    private T data;

    public Result(){}

    private Result(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(T data){
        return new Result(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Result<T> success(String msg, T data){
        return new Result(SUCCESS_CODE, msg, data);
    }

    public static Result error(CodeMsg codeMsg) {
        return new Result(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    public static Result defaultError() {
        return new Result(ERROR_CODE, ERROR_MESSAGE, null);
    }

    public boolean hasError(){
        return this.code != SUCCESS_CODE;
    }

}
