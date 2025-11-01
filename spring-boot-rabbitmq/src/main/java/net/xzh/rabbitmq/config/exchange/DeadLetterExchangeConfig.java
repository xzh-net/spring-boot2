package net.xzh.rabbitmq.config.exchange;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 死信队列配置 
 * @author xzh
 *
 */
@Configuration
public class DeadLetterExchangeConfig {

	// 死信队列相关常量
	public static final String DEAD_LETTER_QUEUE = "dlx.queue.user.order";
	public static final String DEAD_LETTER_ROUTING_KEY = "dlx.routing.user.order";

	// 死信接收队列相关常量
	public static final String DEAD_LETTER_RECEIVE_QUEUE = "dlx.receive.queue.user.order";
	public static final String DEAD_LETTER_RECEIVE_ROUTING_KEY = "dlx.receive.routing.user.order";

	/**
	 * 死信交换机 - 用于接收原始延迟消息 消息发送到此交换机，根据TTL过期后成为死信
	 */
	@Bean
	public DirectExchange deadLetterExchange() {
		return new DirectExchange(AMPQConstant.EXCHANGE_DLX);
	}

	/**
	 * 死信队列 - 设置TTL和死信转发参数 
	 * 队列参数说明： 
	 * - x-dead-letter-exchange: 死信转发交换机 
	 * - x-dead-letter-routing-key: 死信转发路由键 
	 * - x-message-ttl: 消息存活时间（可在发送消息时单独设置）
	 */
	@Bean
	public Queue deadLetterQueue() {
		Map<String, Object> arguments = new HashMap<>();
		// 设置死信转发交换机
		arguments.put("x-dead-letter-exchange", AMPQConstant.EXCHANGE_DLX_RECEIVE);
		// 设置死信转发路由键
		arguments.put("x-dead-letter-routing-key", DEAD_LETTER_RECEIVE_ROUTING_KEY);
		// 可以设置队列级别的TTL（可选，通常在生产消息时设置更灵活）
		// arguments.put("x-message-ttl", 60000); // 60秒

		return new Queue(DEAD_LETTER_QUEUE, // 队列名称
				true, // 持久化
				false, // 非独占
				false, // 非自动删除
				arguments // 队列参数
		);
	}

	/**
	 * 绑定死信队列到死信交换机
	 */
	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
	}

	/**
	 * 死信接收交换机 - 用于接收从死信队列转发的消息 实际业务消费者监听绑定到此交换机的队列
	 */
	@Bean
	public DirectExchange deadLetterReceiveExchange() {
		return new DirectExchange(AMPQConstant.EXCHANGE_DLX_RECEIVE);
	}

	/**
	 * 死信接收队列 - 实际处理死信消息的队列
	 */
	@Bean
	public Queue deadLetterReceiveQueue() {
		return new Queue(DEAD_LETTER_RECEIVE_QUEUE, true, // 持久化
				false, // 非独占
				false // 非自动删除
		);
	}

	/**
	 * 绑定死信接收队列到死信接收交换机
	 */
	@Bean
	public Binding deadLetterReceiveBinding() {
		return BindingBuilder.bind(deadLetterReceiveQueue()).to(deadLetterReceiveExchange())
				.with(DEAD_LETTER_RECEIVE_ROUTING_KEY);
	}

	  /**
     * 死信队列工作流程：
     * 1. 生产者发送消息到 deadLetterExchange，路由键为 DEAD_LETTER_ROUTING_KEY
     * 2. 消息进入 deadLetterQueue，并设置TTL（消息级别或队列级别）
     * 3. 消息在 deadLetterQueue 中等待TTL过期
     * 4. TTL过期后，消息成为死信，被转发到 deadLetterReceiveExchange
     * 5. 死信接收交换机将消息路由到 deadLetterReceiveQueue
     * 6. 消费者从 deadLetterReceiveQueue 获取消息进行业务处理
     */
}