package net.xzh.sockjs.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	@Autowired
	private SimpMessagingTemplate template;

	/*广播*/
	@MessageMapping("/broadcast")
	@SendTo("/topic/broadcast")
	public String say(String msg) {
		return msg;
	}

	@GetMapping("/send")
	public String msgReply(@RequestParam String msg) {
		template.convertAndSend("/topic/broadcast", msg);
		return msg;
	}
	
	/*P2P*/
	@MessageMapping("/one")
    public void sendToUserByTemplate(Map<String,String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = "来自" + fromUserId + "消息:" + params.get("msg");
        template.convertAndSendToUser(toUserId,"/topic", msg);
    }
	
	/**
     * 订阅模式，只是在订阅的时候触发，可以理解为：访问——>返回数据
     * @param id
     * @return
     */
    @SubscribeMapping("/subscribe/{id}")
    public String subscribe(@DestinationVariable Long id) {
        return "success";
    }

}
