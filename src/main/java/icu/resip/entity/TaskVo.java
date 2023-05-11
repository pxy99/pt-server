package icu.resip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Peng
 * @Date 2022/4/16
 */
@Getter
@Setter
public class TaskVo {

    private Long id;

    private Long uid;

    private String nickName;

    private String avatarUrl;

    private String pickAddress;

    private String receiveAddress;

    private int amount;

    private String weight;

    private String money;

    private String thingType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

}
