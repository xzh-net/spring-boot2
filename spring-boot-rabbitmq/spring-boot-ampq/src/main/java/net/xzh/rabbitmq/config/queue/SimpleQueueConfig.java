package net.xzh.rabbitmq.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.CommonConstant;

/**
 * 简单工作队列模式配置
 */
@Configuration
public class SimpleQueueConfig {

    /**
     * 创建简单工作队列
     * 简单队列模式：一个生产者，一个消费者，一对一的消息传递
     * 
     * 队列参数说明：
     * - name: 队列名称
     * - durable: 是否持久化（true-服务器重启后队列依然存在）
     * - exclusive: 是否独占（true-仅限此连接使用）
     * - autoDelete: 是否自动删除（true-没有消费者时自动删除）
     * 
     * 推荐配置：持久化、非独占、非自动删除，确保消息可靠性
     */
    @Bean
    public Queue simpleQueue() {
        return new Queue(
            CommonConstant.QUEUE_SIMPLE,  // 队列名称
            true,                         // 持久化
            false,                        // 非独占
            false                         // 非自动删除
        );
    }
}