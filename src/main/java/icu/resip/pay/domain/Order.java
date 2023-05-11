package icu.resip.pay.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author Peng
 * @Date 2022/4/10
 */
@Getter
@Setter
public class Order {

    private Long id;

    private String transactionId;

    private String orderNo;

    private Long uid;

    private String name;

    private BigDecimal money;

    // 0-待支付，1-已支付，2-支付失败
    private int status;

    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

}
