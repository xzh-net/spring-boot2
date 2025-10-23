package net.xzh.activemq.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * JmsMessagingTemplate测试
 *
 */
@RestController
public class ActiveMQController {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;


	/**
	 * 发送queue消息
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/sendQueueMsg", method = RequestMethod.GET)
	public String sendQueueMsg(@RequestParam String msg) {
		jmsMessagingTemplate.convertAndSend(new ActiveMQQueue("queue"), msg);
		return "发送成功"+ System.currentTimeMillis();
	}

	/**
	 * 发送topic消息
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/sendTopicMsg", method = RequestMethod.GET)
	public String sendTopicMsg(@RequestParam String msg) {
		jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("topic"), msg);
		return "发送成功"+ System.currentTimeMillis();
	}
	
}