package net.xzh.rabbit.exchange;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于TTL和死信队列实现延迟任务
 * @author Administrator
 *
 */
@Configuration
public class DeadLetterConfig {
	
	public static final String DXL_EXCHANGE_NAME = "user.order.dxl_exchange";	//死信交换机
	public static final String DXL_QUEUE_NAME = "user.order.dxl_queue";	//死信队列
	public static final String DXL_ROUTING_KEY = "user.order.dxl_key"; //路由key
	
	public static final String RECEIVE_EXCHANGE_NAME = "user.order.receive_exchange";	//接受死信交换机
	public static final String RECEIVE_QUEUE_NAME = "user.order.receive_queue";	//接受死信队列
	public static final String RECEIVE_ROUTING_KEY = "user.order.receive_key"; //接受路由key

	
	/**
	 * 死信交换机
	 *
	 * @return
	 */
	@Bean
	public DirectExchange userOrderDelayExchange() {
		return new DirectExchange(DXL_EXCHANGE_NAME);
	}

	/**
	 * 死信队列
	 *
	 * @return
	 */
	@Bean
	public Queue userOrderDelayQueue() {
		Map<String, Object> map = new HashMap<>(16);
		map.put("x-dead-letter-exchange", RECEIVE_EXCHANGE_NAME);
		map.put("x-dead-letter-routing-key", RECEIVE_ROUTING_KEY);
		return new Queue("user.order.delay_queue", true, false, false, map);
	}

	/**
	 * 给死信队列绑定交换机
	 *
	 * @return
	 */
	@Bean
	public Binding userOrderDelayBinding() {
		return BindingBuilder.bind(userOrderDelayQueue()).to(userOrderDelayExchange()).with(DXL_ROUTING_KEY);
	}

	/**
	 * 死信接收交换机
	 *
	 * @return
	 */
	@Bean
	public DirectExchange userOrderReceiveExchange() {
		return new DirectExchange(RECEIVE_EXCHANGE_NAME);
	}

	/**
	 * 死信接收队列
	 *
	 * @return
	 */
	@Bean
	public Queue userOrderReceiveQueue() {
		return new Queue(RECEIVE_QUEUE_NAME);
	}

	/**
	 * 死信交换机绑定消费队列
	 *
	 * @return
	 */
	@Bean
	public Binding userOrderReceiveBinding() {
		return BindingBuilder.bind(userOrderReceiveQueue()).to(userOrderReceiveExchange())
				.with(RECEIVE_ROUTING_KEY);
	}
	
}