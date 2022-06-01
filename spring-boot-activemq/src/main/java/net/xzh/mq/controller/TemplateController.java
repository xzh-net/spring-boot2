package net.xzh.mq.controller;

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
import net.xzh.mq.common.model.CommonResult;

/**
 * activemq模板
 * @author CR7
 *
 */

@Api(tags = "activemq模板")
@RequestMapping("/active")
@RestController
public class TemplateController {
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;


	@ApiOperation("发送queue消息")
	@Transactional
	@RequestMapping(value = "/sendQueueMsg", method = RequestMethod.GET)
	public CommonResult<Object> sendQueueMsg(@RequestParam String msg) {
		this.jmsTemplate.convertAndSend(new ActiveMQQueue("queue"), msg);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("发送topic消息")
	@RequestMapping(value = "/sendTopicMsg", method = RequestMethod.GET)
	public CommonResult<Object> sendTopicMsg(@RequestParam String msg) {
		this.jmsTemplate.convertAndSend(new ActiveMQTopic("topic"), msg);
		return CommonResult.success(System.currentTimeMillis());
	}
}