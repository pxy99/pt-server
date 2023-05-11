package icu.resip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * websocket消息传输对象
 * @Author: Peng
 * @Date: 2022/4/2
 */
@Getter
@Setter
public class ChatMag {

    private Long fromId;

    private String fromName;

    private String fromImg;

    private Long toId;

    private String content;

    private int type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

}
