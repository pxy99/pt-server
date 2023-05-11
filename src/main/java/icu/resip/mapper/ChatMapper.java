package icu.resip.mapper;

import icu.resip.domain.chat.ChatList;
import icu.resip.domain.chat.ChatMessage;
import icu.resip.entity.ChatListVo;
import icu.resip.entity.ChatMessageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Peng
 * @Date: 2022/4/2
 */
@Repository
public interface ChatMapper {

    /**
     * 更改chat_list表中friend_window字段
     * @param uid
     * @param isWindow
     */
    void updateFriendWindow(@Param("uid") Long uid, @Param("isWindow") boolean isWindow);

    /**
     * 通过双方查询表chat_list数据
     * @param uid
     * @param friendId
     * @return
     */
    ChatList findOne(@Param("uid") Long uid, @Param("friendId") Long friendId);

    /**
     * 插入一条数据到chat_list表中
     * @param chatList
     */
    void insertList(ChatList chatList);

    /**
     * 给chat_list表中unread字段值+1
     * @param uid
     * @param friendId
     */
    void addUnread(@Param("uid") Long uid, @Param("friendId") Long friendId);

    /**
     * 设置chat_list表中unread字段值为0
     * @param uid
     * @param friendId
     */
    void clearUnread(@Param("uid") Long uid, @Param("friendId") Long friendId);

    /**
     * 在chat_message表插入一条数据
     * @param chatMessage
     */
    void saveMessage(ChatMessage chatMessage);

    /**
     * 查询消息列表好友数据
     * @param uid
     * @return
     */
    List<ChatListVo> selectList(@Param("uid") Long uid);

    /**
     * 查询修后一条消息内容
     * @param fromId
     * @param toId
     * @return
     */
    ChatMessage selectLastContent(@Param("fromId") Long fromId, @Param("toId") Long toId);

    /**
     * 查询聊天记录
     * @param fromId
     * @param toId
     * @return
     */
    List<ChatMessageVo> selectMessage(@Param("fromId") Long fromId, @Param("toId") Long toId);

    /**
     * 查询chat_list表中weight字段最大值
     * @param uid
     * @return
     */
    int selectMaxWeight(Long uid);

    /**
     * 给chat_list表中weight值+1
     * @param listId
     * @return
     */
    int updateWeight(@Param("listId") Long listId, @Param("weight") int weight);

    /**
     * 更改chat_list表中status值
     * @param listId
     * @param uid
     * @return
     */
    int updateStatus(@Param("listId") Long listId, @Param("uid") Long uid, @Param("status") boolean status);
}
