package net.xzh.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.mqtt.client.EmqClient;

/**
 * 发布订阅
 */
@RestController
@RequestMapping("/mqtt")
public class MqttController {

	@Autowired
	private EmqClient emqClient;

	@RequestMapping("/pub")
	public String pub(@RequestParam("topic") String topic, @RequestParam("message") String message) {
		emqClient.publish(true, topic, message);
		return "发送成功" + System.currentTimeMillis();
	}
	
	@RequestMapping("/sub")
	public String sub(@RequestParam("topic") String topic) {
		emqClient.subscribe(topic, 2);
		return "订阅成功" + System.currentTimeMillis();
	}
	
	@RequestMapping("/unsub")
	public String unsub(@RequestParam("topic") String topic) {
		emqClient.unsubscribe(topic);
		return "取消成功" + System.currentTimeMillis();
	}
}
