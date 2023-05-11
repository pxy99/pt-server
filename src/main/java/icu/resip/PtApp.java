package icu.resip;

import icu.resip.websocket.server.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @Author: Peng
 * @Date: 2022/3/29
 */
@MapperScan(basePackages = "icu.resip.mapper")
@EnableTransactionManagement
@SpringBootApplication
@EnableWebSocket
public class PtApp {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(PtApp.class);
        ConfigurableApplicationContext context = springApplication.run(args);
        // 解决WebSocket不能注入的问题
        WebSocketServer.setApplicationContext(context);
    }

}
