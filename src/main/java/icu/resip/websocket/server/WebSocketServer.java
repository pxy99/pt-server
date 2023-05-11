package icu.resip.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import icu.resip.entity.ChatMag;
import icu.resip.exception.LogicException;
import icu.resip.redis.service.UserRedisService;
import icu.resip.result.CommonCodeMsg;
import icu.resip.service.ChatService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/api/socket/server/{uid}")
@Slf4j
@Component
public class WebSocketServer {

    private Session session;

    // 保存客户端连接会话
    public static ConcurrentHashMap<Long, WebSocketServer> clients = new ConcurrentHashMap<>();

    //获取全局容器，用于注入service层实现类
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    private ChatService chatService;

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") Long uid) {
        log.info("---------------客户端连接 -> {}----------------", uid);

        // 初始化service层实现类
        this.chatService = applicationContext.getBean(ChatService.class);

        // 保存会话
        this.session = session;
        clients.put(uid,this);

        // 更改自己状态为在线
        chatService.updateWindow(uid, true);
    }

    @OnMessage
    public void onMessage(String msg, @PathParam("uid") Long uid) {
        log.info("-------------{}发送了消息----{}---------------", uid, msg);

        // 因为收到的消息是json格式的字符串，所以需要将其反序列化为ChatMsg对象
        ChatMag chatMag = JSONObject.parseObject(msg, ChatMag.class);

        // 封装发送时间到对象中
        chatMag.setSendTime(new Date());

        // TODO 获取接收者会话 记得把fromId改成toId
        WebSocketServer webSocketServer = clients.get(chatMag.getToId());
        if (webSocketServer != null) {
            Session toSession = webSocketServer.session;

            // 判断接收者是否在线，如果在线，将消息发送给接收者
            if (toSession != null) {
                toSession.getAsyncRemote().sendText(JSON.toJSONString(chatMag));
            }
        }

        // 如果不在线，保存聊天记录，记录接收者消息未读数
        chatService.saveChatMsg(chatMag);
    }

    @OnClose
    public void onClose(@PathParam( "uid") Long uid) {
        log.info("---------------{} 断开连接----------------", uid);

        // 移除当前会话
        clients.remove(uid);

        // 更改自己状态为离线
        chatService.updateWindow(uid, false);
    }

    @OnError
    public void onError(Throwable error, @PathParam("uid") Long uid) {
        log.error("----------------{} 连接异常-{}---------------", uid, error.getMessage());
    }
}