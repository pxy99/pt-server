package icu.resip.service;

import icu.resip.entity.ChatListVo;
import icu.resip.entity.ChatMag;
import icu.resip.entity.ChatMessageVo;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/4/2
 */
public interface ChatService {

    /**
     * 保存聊天记录
     * @param chatMag
     */
    void saveChatMsg(ChatMag chatMag);

    /**
     * 更改在线状态
     * @param uid
     * @param isWindow
     */
    void updateWindow(Long uid, boolean isWindow);

    /**
     * 获取此用户的消息列表
     * @param token
     * @return
     */
    List<ChatListVo> getChatList(String token);

    /**
     * 获取这个对话的聊天记录
     * @param token
     * @param toId
     * @return
     */
    List<ChatMessageVo> getChatMessage(String token, Long toId);

    /**
     * 将消息置顶
     * @param token
     */
    void putTop(String token, Long listId);

    /**
     * 移除此对话
     * @param listId
     * @param token
     */
    void deleteList(Long listId, String token);
}
