package net.xzh.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.xzh.chat.server.ChatServer;
/**
 * 程序启动同时启动聊天服务器
 * @author Kevin
 * @date 2020/12/25 17:24
 *
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        String host="172.17.17.165";
        int port=1111;
        ChatServer.serverStart(host,port);
    }
}
