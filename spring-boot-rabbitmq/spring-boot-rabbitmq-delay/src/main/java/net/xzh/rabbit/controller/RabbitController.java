package net.xzh.rabbit.controller;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.rabbit.common.model.CommonResult;
import net.xzh.rabbit.domain.OrderItem;
import net.xzh.rabbit.exchange.AcknowledgeConfig;
import net.xzh.rabbit.exchange.DeadLetterConfig;
import net.xzh.rabbit.exchange.RabbitMQConfirmAndReturn;

/**
 * MQ测试
 * @author CR7
 *
 */
@Api(tags = "Rabbit测试")
@RestController
@RequestMapping("/rabbitMq")
public class RabbitController {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate2;
	
	@Autowired
	private RabbitMQConfirmAndReturn rabbitMQConfirmAndReturn;
	
	@PostConstruct
	public void initRabbitTemplate() {
		rabbitTemplate2.setMandatory(true);
		rabbitTemplate2.setConfirmCallback(rabbitMQConfirmAndReturn);
		rabbitTemplate2.setReturnCallback(rabbitMQConfirmAndReturn);
	}

	@ApiOperation("投递确认&消费确认")
	@RequestMapping(value = "/manual", method = RequestMethod.GET)
	public CommonResult<?> Manual(@RequestParam String orderId) {
		Message message = MessageBuilder.withBody(orderId.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").setMessageId(orderId)
				.build();
		// 构建回调返回的数据
		CorrelationData correlationData = new CorrelationData(orderId);
		// 发送消息
		rabbitTemplate2.convertAndSend(AcknowledgeConfig.ITEM_TOPIC_EXCHANGE, "item.springboot-rabbitmq", message,
				correlationData);
		return CommonResult.success(orderId);
	}

	@ApiOperation("发送延迟消息")
	@RequestMapping(value = "/delay", method = RequestMethod.GET)
	public CommonResult<?> delay(@RequestParam String orderId) {
		// 给延迟队列发送消息，彼此间隔不同无任何影响
		OrderItem order = new OrderItem();
		order.setId(Long.valueOf(orderId));
		order.setProductName("延迟战甲" + System.currentTimeMillis());
		rabbitTemplate.convertAndSend("delay_exchange", "delay_queue", JSONUtil.toJsonStr(order), message -> {
			// 配置消息的过期时间
			message.getMessageProperties().setHeader("x-delay", 5000L);
			return message;
		}, new CorrelationData(orderId));
		return CommonResult.success(orderId);
	}

	@ApiOperation("发送dxl死信消息")
	@RequestMapping(value = "/dxl", method = RequestMethod.GET)
	public CommonResult<?> dxl(@RequestParam String orderId) {
		OrderItem order = new OrderItem();
		order.setId(Long.valueOf(orderId));
		order.setProductName("死信头盔" + System.currentTimeMillis());
		rabbitTemplate.convertAndSend(DeadLetterConfig.DXL_EXCHANGE_NAME, DeadLetterConfig.DXL_ROUTING_KEY,
				JSONUtil.toJsonStr(order), message -> {
					message.getMessageProperties().setExpiration("3000");
					return message;
				}, new CorrelationData(orderId));
		return CommonResult.success(null);
	}
}
