package icu.resip.domain.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Peng
 * @Date: 2022/4/6
 */
@Getter
@Setter
public class ChatList {

    private Long id;

    private Long uid;

    private Long friendId;

    private boolean friendWindow;

    private Long unread;

    private boolean status;

    private Integer weight;

}
