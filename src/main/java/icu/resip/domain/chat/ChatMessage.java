package icu.resip.domain.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: Peng
 * @Date: 2022/4/6
 */
@Getter
@Setter
public class ChatMessage {

    private Long id;

    private Long fromId;

    private Long toId;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    private Integer type;

}
