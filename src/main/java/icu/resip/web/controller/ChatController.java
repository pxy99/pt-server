package icu.resip.web.controller;

import icu.resip.annotation.CheckToken;
import icu.resip.constants.CommonConstants;
import icu.resip.entity.ChatListVo;
import icu.resip.entity.ChatMessageVo;
import icu.resip.result.Result;
import icu.resip.service.ChatService;
import icu.resip.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 消息资源控制器
 * @Author: Peng
 * @Date: 2022/4/2
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private ChatService chatService;

    @Autowired
    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    // 查询消息列表
    @CheckToken
    @GetMapping("/list")
    public Result<List<ChatListVo>> chatList(HttpServletRequest request) {
        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 获取消息列表
        List<ChatListVo> chatListVoList = chatService.getChatList(token);

        return Result.success(chatListVoList);
    }

    // 查询聊天记录
    @CheckToken
    @GetMapping("/record/{id}")
    public Result<List<ChatMessageVo>> chatRecord(HttpServletRequest request, @PathVariable("id") Long toId) {
        // 校验参数
        AssertUtil.isParamsNull(toId);

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 查询聊天记录
        List<ChatMessageVo> chatMessageVoList = chatService.getChatMessage(token, toId);

        return Result.success(chatMessageVoList);
    }

    // 置顶对话
    @CheckToken
    @PutMapping("/top/{id}")
    public Result<Object> putTop(HttpServletRequest request, @PathVariable("id") Long listId) {
        // 校验参数
        AssertUtil.isParamsNull(listId);

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 将消息置顶
        chatService.putTop(token, listId);

        return Result.success(null);
    }

    // 移除对话
    @CheckToken
    @DeleteMapping("/list/{id}")
    public Result<Object> deleteList(@PathVariable("id") Long listId, HttpServletRequest request) {
        // 校验参数
        AssertUtil.isParamsNull(listId);

        // 获取token
        String token = request.getHeader(CommonConstants.TOKEN_NAME);

        // 移除此对话
        chatService.deleteList(listId, token);

        return Result.success(null);
    }

}
