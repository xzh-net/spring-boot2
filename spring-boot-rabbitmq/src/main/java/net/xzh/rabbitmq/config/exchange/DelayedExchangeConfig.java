package net.xzh.rabbitmq.config.exchange;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 延迟消息交换机配置，安装delayed插件
 */
@Configuration
public class DelayedExchangeConfig {

	// 延迟队列相关常量
	public static final String DELAYED_QUEUE = "delay.queue";
	public static final String DELAYED_ROUTING_KEY = "delay.routing.key";

	/**
	 * 延迟队列 - 用于接收延迟到期的消息
	 */
	@Bean
	public Queue delayedQueue() {
		return new Queue(DELAYED_QUEUE, true, // 持久化
				false, // 非独占
				false // 非自动删除
		);
	}

	/**
	 * 自定义交换器
	 */
	@Bean
	public CustomExchange delayedExchange() {
		Map<String, Object> arguments = new HashMap<>();
		// 设置底层交换机的类型，支持：direct、topic、fanout等
		arguments.put("x-delayed-type", "direct");

		return new CustomExchange(AMPQConstant.EXCHANGE_DELAY, // 交换机名称
				"x-delayed-message", // 交换机类型（插件提供）
				true, // 持久化
				false, // 非自动删除
				arguments // 交换机参数
		);
	}

	/**
	 * 绑定延迟队列到延迟交换机
	 */
	@Bean
	public Binding delayedBinding(Queue delayedQueue, CustomExchange delayedExchange) {
		return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs(); // 自定义交换机需要noargs()
	}

}