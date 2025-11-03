package net.xzh.rabbitmq.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 简单工作队列模式配置
 */
@Configuration
public class SimpleQueueConfig {

	/**
	 * 创建简单工作队列 简单队列模式：一个生产者，一个消费者，一对一的消息传递
	 */
	@Bean
	public Queue simpleQueue() {
		return new Queue(AMPQConstant.QUEUE_SIMPLE, // 队列名称
				true, // 持久化
				false, // 非独占
				false // 非自动删除
		);
	}
}