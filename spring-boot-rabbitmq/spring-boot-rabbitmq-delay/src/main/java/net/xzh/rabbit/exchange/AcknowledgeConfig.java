package net.xzh.rabbit.exchange;

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
 * 确认方式交换机配置
 * 当前以主题模式测试，生产中可视情况修改交换机类型
 */
@Configuration
public class AcknowledgeConfig {
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