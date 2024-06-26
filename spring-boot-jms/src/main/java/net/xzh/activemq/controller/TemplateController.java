package net.xzh.activemq.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.activemq.common.model.CommonResult;

/**
 * jmsMessagingTemplate模板
 * @author CR7
 *
 */
@Api(tags = "jmsMessagingTemplate")
@RequestMapping("/active")
@RestController
public class TemplateController {
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;


	@ApiOperation("发送queue消息")
	@Transactional
	@RequestMapping(value = "/sendQueueMsg", method = RequestMethod.GET)
	public CommonResult<Object> sendQueueMsg(@RequestParam String msg) {
		jmsMessagingTemplate.convertAndSend(new ActiveMQQueue("queue"), msg);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("发送topic消息")
	@RequestMapping(value = "/sendTopicMsg", method = RequestMethod.GET)
	public CommonResult<Object> sendTopicMsg(@RequestParam String msg) {
		jmsMessagingTemplate.convertAndSend(new ActiveMQTopic("topic"), msg);
		return CommonResult.success(System.currentTimeMillis());
	}
	
}