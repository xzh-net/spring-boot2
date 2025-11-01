package net.xzh.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.rabbitmq.common.constant.CommonConstant;

/**
 * RabbitTemplate测试
 * @author xzh
 *
 */
@RestController
@RequestMapping("/rabbit")
public class TemplateController {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 简单模式
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public String simple(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(CommonConstant.QUEUE_SIMPLE, msg);
		return "Simple 发送成功";
	}

	/**
	 * 工作模式
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public String work(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(CommonConstant.QUEUE_WORK, msg);
		return "Work 发送成功";
	}

	/**
	 * 订阅模式，扇形交换机
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public String publish(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(CommonConstant.EXCHANGE_FANOUT, "", msg);
		return "Publish 发送成功";
	}

	/**
	 * 路由模式，直接交换机
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public String direct(@RequestParam String msg) {
		String key = "";
		if (msg.startsWith("error")) {
			key = "error";
		} else if (msg.startsWith("warn")) {
			key = "warn";
		} else if (msg.startsWith("info")) {
			key = "info";
		} else if (msg.startsWith("debug")) {
			key = "debug";
		} else {
			key = "other";
		}
		rabbitTemplate.convertAndSend(CommonConstant.EXCHANGE_DIRECT, key, msg);
		return "Direct 发送成功";
	}

	/**
	 * 主题模式，主题交换机
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/match", method = RequestMethod.GET)
	public String match(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(CommonConstant.EXCHANGE_TOPIC, msg, msg);
		return "Topic 发送成功";
	}
}
