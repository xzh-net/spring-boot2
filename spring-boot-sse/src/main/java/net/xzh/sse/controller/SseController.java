package net.xzh.sse.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import net.xzh.sse.server.SseConnectionManager;

@RestController
public class SseController {

    @RequestMapping(value = "/connect",method = RequestMethod.GET)
    public SseEmitter connect(Principal principal){
        SseEmitter sseEmitter = SseConnectionManager.connect(principal.getName());
        return sseEmitter;
    }

    /**
     * 向指定用户发送消息
     */
    @RequestMapping(value = "/send/{id}", method = RequestMethod.GET)
    public String sendMsg(@PathVariable String id,@RequestParam("message") String message) {
        SseConnectionManager.sendMessage(id,message);
        return "向"+id+"号用户发送信息，"+message+"，消息发送成功";
    }

    /**
     * 向所有用户发送消息
     */
    @RequestMapping(value = "/send/all", method = RequestMethod.GET)
    public String sendMsg2AllUser(@RequestParam("message") String message) {
        SseConnectionManager.batchSendMessage(message);
        return "向所有用户发送信息，"+message+"，消息发送成功";
    }

    /**
     * 关闭用户连接
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.GET)
    public String closeSse(@PathVariable String id) {
        SseConnectionManager.removeUser(id);
        return "关闭"+id+"号连接。当前连接用户有："+SseConnectionManager.getIds();
    }
}
