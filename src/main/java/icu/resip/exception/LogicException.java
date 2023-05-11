package icu.resip.exception;

import icu.resip.result.CodeMsg;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Peng
 * @Date 2021/11/15 17:52
 * @Version 1.0
 */
@Getter
@Setter
public class LogicException extends RuntimeException {

    private CodeMsg codeMsg;

    public LogicException(CodeMsg codeMsg){
        this.codeMsg = codeMsg;
    }

}
