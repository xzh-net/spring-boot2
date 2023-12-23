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

/**
 * 群聊
 * 
 * @author CR7
 *
 */
@RestController
public class ChatController {
	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * 后端调用，注解转发到具体broadcast主题上
	 * @param msg
	 * @return
	 */
	@MessageMapping("/sendToAll1")
	@SendTo("/topic/broadcast")
	public String say(String msg) {
		return msg;
	}

	/**
	 * 后端调用的另外一种写法，代码识别转到主题
	 * @param msg
	 */
	@GetMapping("/sendToAll4")
	@MessageMapping("/sendToAll2")
	public void sendToAllByTemplate(@RequestParam String msg) {
		template.convertAndSend("/topic/broadcast", msg);
	}

	/**
	 *  前端调用，做为无服务端情况下前端发送调用的异步记录日志，可以没有
	 * @param msg
	 * @return
	 */
	@MessageMapping("/topic/web")
	public String sendToAll(String msg) {
		System.out.println("前端群发异步日志：" + msg);
		return msg;
	}

	/**
	 * 点对点
	 * @param params
	 */
	@MessageMapping("/sendToUser")
	public void sendToUserByTemplate(Map<String, Object> params) {
		String toUserId = String.valueOf(params.get("toUserId"));
		params.put("sentTime", System.currentTimeMillis());
		String destination = "/queue/user_" + toUserId;
		template.convertAndSend(destination, params);
	}

}
