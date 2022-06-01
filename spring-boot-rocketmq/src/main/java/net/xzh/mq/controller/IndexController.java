package net.xzh.mq.controller;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.mq.common.model.CommonResult;
import net.xzh.mq.domain.Order;

@Api(tags = "消息发送测试")
@RestController
@RequestMapping("/rocket")
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	/**
	 * 同步发送
	 * 
	 * @param pid
	 * @return
	 */
	
	@ApiOperation("同步发送")
	@RequestMapping(value = "/syncSend/{pid}", method = RequestMethod.GET)
	public CommonResult syncSend(@PathVariable("pid") Integer pid) {
		Order order = new Order();
		order.setUid(1);
		order.setUsername("xzh");
		order.setPid(pid);
		order.setPname("潜水望远镜");
		order.setPprice(698.50);
		order.setNumber(1);
		rocketMQTemplate.convertAndSend("order-topic", order);
		return CommonResult.success(System.currentTimeMillis());
	}

	/**
	 * 异步消息
	 * @param pid
	 * @return
	 */
	@ApiOperation("异步发送")
	@RequestMapping(value = "/asyncSend/{pid}", method = RequestMethod.GET)
	public CommonResult asyncSend(@PathVariable("pid") Integer pid) {
		Order order = new Order();
		order.setUid(1);
		order.setUsername("异步消息");
		order.setPid(pid);
		order.setPname("超人衬衫");
		order.setPprice(280.00);
		order.setNumber(1);
		// 参数一: topic, 如果想添加tag 可以使用"topic:tag"的写法
		// 参数二: 消息内容
		// 参数三: 回调函数, 处理返回结果
		rocketMQTemplate.asyncSend("order-topic", order, new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				log.info("发送成功，{}",sendResult);
			}

			@Override
			public void onException(Throwable throwable) {
				log.info("发送失败，{}",throwable);
			}
		});
		return CommonResult.success(System.currentTimeMillis());
	}

	/**
	 * 单向发送，可靠性要求低的场景，例如日志手机
	 * @param pid
	 * @return
	 */
	@ApiOperation("单向发送")
	@RequestMapping(value = "/sendOneWay/{pid}", method = RequestMethod.GET)
	public CommonResult sendOneWay(@PathVariable("pid") Integer pid) {
		// 参数一: topic, 如果想添加tag 可以使用"topic:tag"的写法
		// 参数二: 消息内容
		// 参数三: 回调函数, 处理返回结果
		for (int i = 0; i < 10; i++) {
			Order order = new Order();
			order.setUid(i);
			order.setUsername("单向消息");
			order.setPid(pid);
			order.setPname("肉包子");
			order.setPprice(2.50);
			order.setNumber(1);
			rocketMQTemplate.sendOneWay("order-topic", order);
		}
		return CommonResult.success(System.currentTimeMillis());
	}

	// 单向顺序消息
	@ApiOperation("单向顺序消息")
	@RequestMapping(value = "/sendOneWayOrderly/{pid}", method = RequestMethod.GET)
	public CommonResult sendOneWayOrderly(@PathVariable("pid") Integer pid) {
		for (int i = 0; i < 10; i++) {
			// 第三个参数的作用是用来决定这些消息发送到哪个队列的上的
			Order order = new Order();
			order.setUid(i);
			order.setUsername("单向顺序");
			order.setPid(pid);
			order.setPname("手机充值卡");
			order.setPprice(97.50);
			order.setNumber(1);
			rocketMQTemplate.sendOneWayOrderly("order-topic", order, "xx");
		}
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("延时2s消息")
	@RequestMapping(value = "/sendDelay/{pid}", method = RequestMethod.GET)
	public CommonResult sendDelay(@PathVariable("pid") Integer pid) {
		SendResult result = rocketMQTemplate.syncSend("delay-topic", MessageBuilder.withPayload(pid).build(), 3000, 2);
		return CommonResult.success(result.getMsgId());
	}
}
