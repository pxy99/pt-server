package icu.resip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author Peng
 * @Date 2022/4/6
 */
@Getter
@Setter
public class ChatListVo {

    private Long id;

    private Long friendId;

    private String friendName;

    private String friendImg;

    private Long unread;

    private Integer weight;

    private String content;

    private Integer type;

    private String sendTime;

}
