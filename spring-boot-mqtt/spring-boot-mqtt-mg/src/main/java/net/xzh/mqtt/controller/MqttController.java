package net.xzh.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.mqtt.gateway.MqttGateway;

/**
 * 发布测试
 */
@RestController
@RequestMapping("/mqtt")
public class MqttController {

	@Autowired
	private MqttGateway mqttGateway;

	/**
	 * 向指定主题发送消息
	 * @param topic
	 * @param payload
	 * @return
	 */
	@GetMapping("/send")
	public String send(String topic,String payload) {
		mqttGateway.sendToMqtt(topic, payload, 1);
		return "发送成功" + System.currentTimeMillis();
	}
}
