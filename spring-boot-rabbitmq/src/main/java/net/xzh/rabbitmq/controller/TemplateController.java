package net.xzh.rabbitmq.controller;

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
import net.xzh.rabbitmq.common.constant.AMPQConstant;
import net.xzh.rabbitmq.config.exchange.AcknowledgeExchangeConfig;
import net.xzh.rabbitmq.config.exchange.DeadLetterExchangeConfig;
import net.xzh.rabbitmq.config.exchange.DelayedExchangeConfig;
import net.xzh.rabbitmq.domain.OrderDTO;

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
	 * 有回执的template
	 */
	@Autowired
	private RabbitTemplate acknowledgeRabbitTemplate;

	/**
	 * 简单模式
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	public String simple(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(AMPQConstant.QUEUE_SIMPLE, msg);
		return "Simple 发送成功";
	}

	/**
	 * 工作模式
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/work", method = RequestMethod.GET)
	public String work(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(AMPQConstant.QUEUE_WORK, msg);
		return "Work 发送成功";
	}

	/**
	 * 订阅模式，扇形交换机
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public String publish(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(AMPQConstant.EXCHANGE_FANOUT, "", msg);
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
		rabbitTemplate.convertAndSend(AMPQConstant.EXCHANGE_DIRECT, key, msg);
		return "Direct 发送成功";
	}

	/**
	 * 主题模式，主题交换机
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/match", method = RequestMethod.GET)
	public String match(@RequestParam String msg) {
		rabbitTemplate.convertAndSend(AMPQConstant.EXCHANGE_TOPIC, msg, msg);
		return "Topic 发送成功";
	}
	
	/**
	 * dxl死信消息
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/dlx", method = RequestMethod.GET)
	public String dlx(@RequestParam String orderId) {
		OrderDTO order = new OrderDTO();
		order.setId(Long.valueOf(orderId));
		order.setProductName("死信队列头盔" + System.currentTimeMillis());
		rabbitTemplate.convertAndSend(AMPQConstant.EXCHANGE_DLX, DeadLetterExchangeConfig.DEAD_LETTER_ROUTING_KEY,
				JSONUtil.toJsonStr(order), message -> {
					message.getMessageProperties().setExpiration("3000");
					return message;
				}, new CorrelationData(orderId));
		return "DXL 发送成功";
	}
	

	/**
	 * 延迟消息
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/delay", method = RequestMethod.GET)
	public String delay(@RequestParam String orderId) {
		// 给延迟队列发送消息，彼此间隔不同无任何影响
		OrderDTO order = new OrderDTO();
		order.setId(Long.valueOf(orderId));
		order.setProductName("延迟战甲" + System.currentTimeMillis());
		rabbitTemplate.convertAndSend(AMPQConstant.EXCHANGE_DELAY, DelayedExchangeConfig.DELAYED_ROUTING_KEY,
				JSONUtil.toJsonStr(order), message -> {
					// 配置消息的过期时间
					message.getMessageProperties().setHeader("x-delay", 5000L);
					return message;
				}, new CorrelationData(orderId));
		return "delay 发送成功";
	}
	
	/**
	 * 投递确认 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/ack", method = RequestMethod.GET)
	public String ack(@RequestParam String orderId) {
		Message message = MessageBuilder.withBody(orderId.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").setMessageId(orderId)
				.build();
		// 构建回调返回的数据
		CorrelationData correlationData = new CorrelationData(orderId);
		// 发送消息
		acknowledgeRabbitTemplate.convertAndSend(AMPQConstant.EXCHANGE_ACK, AcknowledgeExchangeConfig.ACK_ROUTING_KEY, message,
				correlationData);
		return "ack 发送成功";
	}
}
