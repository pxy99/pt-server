package icu.resip.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Peng
 * @Date 2022/4/18
 */
@Getter
@Setter
public class ReceiveQo {

    private Long id;

    private String captchaVerification;

    private long time;

    private String receiveCode;

}
