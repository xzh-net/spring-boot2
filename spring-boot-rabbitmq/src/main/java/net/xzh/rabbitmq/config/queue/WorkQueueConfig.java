package net.xzh.rabbitmq.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 工作队列模式配置 工作队列模式：一个生产者，多个消费者，消息在消费者之间竞争消费
 */
@Configuration
public class WorkQueueConfig {

	/**
	 * 创建工作队列
	 * 
	 */
	@Bean
	public Queue workQueue() {
		return new Queue(AMPQConstant.QUEUE_WORK, // 队列名称
				true, // 持久化 - 确保任务不丢失
				false, // 非独占 - 允许多个消费者
				false // 非自动删除 - 持久化队列
		);
	}
}