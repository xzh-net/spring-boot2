package net.xzh.rabbit.exchange.manual;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类(手动确认)
 */
@Configuration
public class ManualRabbitConfig {
	// 交换机名称
	public static final String ITEM_TOPIC_EXCHANGE = "item_topic_exchange";
	// 队列名称
	public static final String ITEM_QUEUE = "item_queue";

	// 声明交换机
	@Bean("itemTopicExchange")
	public Exchange topicExchange() {
		return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
	}

	// 声明队列
	@Bean("itemQueue")
	public Queue itemQueue() {
		return QueueBuilder.durable(ITEM_QUEUE).build();
	}

	// 绑定队列和交换机
	@Bean
	public Binding itemQueueExchange(@Qualifier("itemQueue") Queue queue,
			@Qualifier("itemTopicExchange") Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
	}
}