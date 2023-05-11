package icu.resip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Peng
 * @Date 2022/4/7
 */
@Getter
@Setter
public class ChatMessageVo {

    private Long id;

    private Long fromId;

    private String fromName;

    private String fromImg;

    private Long toId;

    private String toName;

    private String toImg;

    private String content;

    private int type;

    private String sendTime;

    public void setSendTime(Date sendTime) {
        this.sendTime = String.valueOf(sendTime.getTime());
    }
}
