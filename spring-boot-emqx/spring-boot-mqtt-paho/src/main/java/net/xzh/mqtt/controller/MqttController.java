package net.xzh.mqtt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping("/send")
	public String send(@RequestParam("topic") String topic, @RequestParam("message") String message) {
		emqClient.publish(true, topic, message);
		return "发送成功" + System.currentTimeMillis();
	}
}
