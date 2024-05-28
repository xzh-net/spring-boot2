package net.xzh.rabbitmq.exchange;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 工作模式
 */
@Configuration
public class WorkQueueConfig {

    @Bean
    public Queue workQueue() {
        return new Queue(CommonConstant.QUEUE_WORK);
    }

}
