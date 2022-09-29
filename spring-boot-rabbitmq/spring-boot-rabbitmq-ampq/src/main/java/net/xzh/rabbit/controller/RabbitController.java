package net.xzh.rabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.rabbit.common.model.CommonResult;
import net.xzh.rabbit.exchange.direct.DirectSender;
import net.xzh.rabbit.exchange.fanout.FanoutSender;
import net.xzh.rabbit.exchange.simple.SimpleSender;
import net.xzh.rabbit.exchange.topic.TopicSender;
import net.xzh.rabbit.exchange.work.WorkSender;

/**
 * Created by macro on 2020/5/19.
 */
@Api(tags = "Rabbit测试")
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

	@Autowired
	private SimpleSender simpleSender;
	@Autowired
	private WorkSender workSender;
	@Autowired
	private FanoutSender fanoutSender;
	@Autowired
	private DirectSender directSender;
	@Autowired
	private TopicSender topicSender;

	@ApiOperation("简单模式")
	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public CommonResult simple(@RequestParam String orderId) {
		simpleSender.send(orderId);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("工作模式")
	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public CommonResult work(@RequestParam String orderId) {
		workSender.send(orderId);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("发布/订阅模式")
	@RequestMapping(value = "/fanout", method = RequestMethod.GET)
	public CommonResult fanout(@RequestParam String orderId) {
		fanoutSender.send(orderId);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("路由模式")
	@RequestMapping(value = "/direct", method = RequestMethod.GET)
	public CommonResult direct(@RequestParam String orderId) {
		directSender.send(orderId);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("通配符模式")
	@RequestMapping(value = "/topic", method = RequestMethod.GET)
	public CommonResult topic(@RequestParam String orderId) {
		topicSender.send(orderId);
		return CommonResult.success(System.currentTimeMillis());
	}
}
