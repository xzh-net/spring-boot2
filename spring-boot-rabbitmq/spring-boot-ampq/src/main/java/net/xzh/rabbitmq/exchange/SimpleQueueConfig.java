package net.xzh.rabbitmq.exchange;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 简单工作模式
 */
@Configuration
public class SimpleQueueConfig {

	@Bean
	public Queue simpleQueue() {
		return new Queue(CommonConstant.QUEUE_SIMPLE);
	}
}
