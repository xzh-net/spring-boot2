package net.xzh.rabbitmq.config.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;
import net.xzh.rabbitmq.config.RabbitMQCallback;

/**
 * 确认方式交换机配置 当前以主题模式测试，生产中可视情况修改交换机类型
 */
@Configuration
public class AcknowledgeExchangeConfig {
	// 队列名称
	public static final String ACK_QUEUE = "ack_queue";

	// 路由键模式常量
	public static final String ACK_ROUTING_KEY = "ack.routing.key";

	// 声明交换机
	@Bean
	public Exchange acknowledgeTopicExchange() {
		return ExchangeBuilder.topicExchange(AMPQConstant.EXCHANGE_ACK).durable(true) // 持久化，服务器重启后依然存在
				.build();
	}

	// 声明队列
	@Bean
	public Queue acknowledgeQueue() {
		return QueueBuilder.durable(ACK_QUEUE).build();
	}

	/**
	 * 绑定队列和交换机
	 * 
	 * 路由模式说明： - "item.#" 匹配以 "item." 开头的所有路由键 - 示例匹配：item.create, item.update,
	 * item.delete.success
	 * 
	 * @param queue    确认队列
	 * @param exchange 主题交换机
	 * @return 绑定关系
	 */
	@Bean
	public Binding itemQueueExchange(Queue acknowledgeQueue, Exchange acknowledgeTopicExchange) {
		return BindingBuilder.bind(acknowledgeQueue).to(acknowledgeTopicExchange).with(ACK_ROUTING_KEY).noargs();
	}

	@Bean
	public RabbitTemplate acknowledgeRabbitTemplate(ConnectionFactory connectionFactory,
			RabbitMQCallback rabbitMQCallback) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		// 设置消息确认回调 - 确认消息是否到达交换机
		rabbitTemplate.setConfirmCallback(rabbitMQCallback);

		// 设置消息退回回调 - 处理不可路由的消息
		rabbitTemplate.setReturnCallback(rabbitMQCallback);

		// 必须设置为 true 才能触发 ReturnCallback
		// 当消息无法路由到任何队列时，会将消息退回给生产者
		rabbitTemplate.setMandatory(true);

		// 开启通道事务，确保操作原子性
		rabbitTemplate.setChannelTransacted(true);

		// 可选配置：设置消息转换器
		// rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

		// 可选配置：设置重试策略
		// rabbitTemplate.setRetryTemplate(retryTemplate());

		return rabbitTemplate;
	}
}