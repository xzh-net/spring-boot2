package net.xzh.rabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.rabbitmq.common.model.CommonResult;
import net.xzh.rabbitmq.config.CommonConstant;

/**
 * RabbitTemplate
 */
@Api(tags = "RabbitTemplate测试")
@RestController
@RequestMapping("/rabbit")
public class TemplateController {

	@Autowired
	private RabbitTemplate template;

	@ApiOperation("简单模式")
	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public CommonResult<?> simple(@RequestParam String messgaes) {
		template.convertAndSend(CommonConstant.QUEUE_SIMPLE, messgaes);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("工作模式")
	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public CommonResult<?> work(@RequestParam String messgaes) {
		template.convertAndSend(CommonConstant.QUEUE_WORK, messgaes);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("发布/订阅模式")
	@RequestMapping(value = "/fanout", method = RequestMethod.GET)
	public CommonResult<?> fanout(@RequestParam String messgaes) {
		template.convertAndSend(CommonConstant.EXCHANGE_FANOUT, "", messgaes);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("路由模式")
	@RequestMapping(value = "/direct", method = RequestMethod.GET)
	public CommonResult<?> direct(@RequestParam String messgaes) {
		String key = "";
		if (messgaes.startsWith("1")) {
			key = "error";
		} else if (messgaes.startsWith("2")) {
			key = "warn";
		} else if (messgaes.startsWith("3")) {
			key = "info";
		} else if (messgaes.startsWith("4")) {
			key = "debug";
		} else {
			key = "other";
		}
		template.convertAndSend(CommonConstant.EXCHANGE_DIRECT, key, messgaes);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("通配符模式")
	@RequestMapping(value = "/topic", method = RequestMethod.GET)
	public CommonResult<?> topic(@RequestParam String messgaes) {
		template.convertAndSend(CommonConstant.EXCHANGE_TOPIC, messgaes, messgaes);
		return CommonResult.success(System.currentTimeMillis());
	}
}
