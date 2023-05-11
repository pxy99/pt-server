package icu.resip.service.impl;

import icu.resip.domain.chat.ChatList;
import icu.resip.domain.chat.ChatMessage;
import icu.resip.entity.ChatListVo;
import icu.resip.entity.ChatMag;
import icu.resip.entity.ChatMessageVo;
import icu.resip.exception.LogicException;
import icu.resip.mapper.ChatMapper;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/4/2
 */
@Service
public class ChatServiceImpl implements ChatService {

    private UserRedisService userRedisService;

    @Autowired
    public void setUserRedisService(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    private ChatMapper chatMapper;

    @Autowired
    public void setChatMapper(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    @Override
    public void saveChatMsg(ChatMag chatMag) {
        Long fromId = chatMag.getFromId();
        Long toId = chatMag.getToId();

        // 判断双方是否是第一次聊天，如果是第一次聊天，在聊天列表中添加两条数据
        ChatList listA = chatMapper.findOne(fromId, toId);
        if (listA == null) {
            listA = new ChatList();
            listA.setUid(fromId);
            listA.setFriendId(toId);
            listA.setFriendWindow(true);
            listA.setUnread(0L);
            listA.setStatus(false);
            listA.setWeight(1);
            chatMapper.insertList(listA);
        }
        ChatList listB = chatMapper.findOne(toId, fromId);
        if (listB == null) {
            listB = new ChatList();
            listB.setUid(toId);
            listB.setFriendId(fromId);
            listB.setFriendWindow(true);
            listB.setUnread(0L);
            listB.setStatus(false);
            listB.setWeight(1);
            chatMapper.insertList(listB);
        }

        // 判断对方是否在线，如果不在线，以对方角度未读数+1
        if (!listA.isFriendWindow()) {
            chatMapper.addUnread(toId, fromId);
        } else {
            // 如果在线，清空对方未读数
            chatMapper.clearUnread(toId, fromId);
        }

        // 如果不是第一次聊天，保存聊天数据
        ChatMessage message = new ChatMessage();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(chatMag.getContent());
        message.setSendTime(chatMag.getSendTime());
        message.setType(chatMag.getType());
        chatMapper.saveMessage(message);
    }

    @Override
    public void updateWindow(Long uid, boolean isWindow) {
        // 更改自己在所有好友列表中的在线状态
        chatMapper.updateFriendWindow(uid, isWindow);
    }

    @Override
    public List<ChatListVo> getChatList(String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询消息列表好友数据
        List<ChatListVo> chatListVoList = chatMapper.selectList(uid);

        // 封装最后一条消息内容及发送时间
        for (ChatListVo chatListVo : chatListVoList) {
            // 查询最后一条消息内容
            ChatMessage message = chatMapper.selectLastContent(uid, chatListVo.getFriendId());
            chatListVo.setContent(message.getContent());
            chatListVo.setType(message.getType());
            chatListVo.setSendTime(String.valueOf(message.getSendTime().getTime()));
        }

        return chatListVoList;
    }

    @Override
    public List<ChatMessageVo> getChatMessage(String token, Long toId) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询聊天记录
        return chatMapper.selectMessage(uid, toId);
    }

    @Override
    public void putTop(String token, Long listId) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 查询关于自己的消息列表权重最高值
        int maxWeight = chatMapper.selectMaxWeight(uid);

        // 给此列表权重+1
        int row = chatMapper.updateWeight(listId, maxWeight + 1);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.CHAT_WEIGHT_ERROR);
        }
    }

    @Override
    public void deleteList(Long listId, String token) {
        // 获取uid
        Long uid = userRedisService.getWxUid(token);

        // 更改此对话状态为true，表示已删除
        int row = chatMapper.updateStatus(listId, uid, true);
        if (row == 0) {
            throw new LogicException(CommonCodeMsg.CHAT_LIST_DEL_ERROR);
        }
    }

}
