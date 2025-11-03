package net.xzh.rabbitmq.config.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 发布订阅模式 - 扇形交换机配置
 */
@Configuration
public class FanoutExchangeConfig {

	// 队列名称常量
	public static final String FANOUT_QUEUE_1 = "fanout.queue1";
	public static final String FANOUT_QUEUE_2 = "fanout.queue2";

	/**
	 * 创建扇形交换机 扇形交换机会将消息路由到所有绑定的队列，忽略路由键
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(AMPQConstant.EXCHANGE_FANOUT);
	}

	/**
	 * 创建队列1 - 持久化队列
	 */
	@Bean
	public Queue fanoutQueue1() {
		// 参数说明：队列名称, 是否持久化, 是否独占, 是否自动删除
		return new Queue(FANOUT_QUEUE_1, true, false, false);
	}

	/**
	 * 创建队列2 - 持久化队列
	 */
	@Bean
	public Queue fanoutQueue2() {
		return new Queue(FANOUT_QUEUE_2, true, false, false);
	}

	/**
	 * 将队列1绑定到扇形交换机 扇形交换机不需要指定路由键
	 */
	@Bean
	public Binding fanoutBinding1(FanoutExchange fanoutExchange, Queue fanoutQueue1) {
		return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
	}

	/**
	 * 将队列2绑定到扇形交换机 扇形交换机不需要指定路由键
	 */
	@Bean
	public Binding fanoutBinding2(FanoutExchange fanoutExchange, Queue fanoutQueue2) {
		return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
	}
}