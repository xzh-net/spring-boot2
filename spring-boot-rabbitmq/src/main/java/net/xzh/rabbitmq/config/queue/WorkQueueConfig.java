package net.xzh.rabbitmq.config.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 工作队列模式配置
 * 工作队列模式：一个生产者，多个消费者，消息在消费者之间竞争消费
 */
@Configuration
public class WorkQueueConfig {

    /**
     * 创建工作队列
     * 
     * 工作队列特点：
     * - 多个消费者竞争消费同一个队列的消息
     * - 默认采用轮询分发（Round-robin）方式
     * - 可以通过prefetchCount设置公平分发
     * 
     * 队列参数说明：
     * - name: 队列名称
     * - durable: 是否持久化（true-服务器重启后队列依然存在）
     * - exclusive: 是否独占（true-仅限此连接使用）
     * - autoDelete: 是否自动删除（true-没有消费者时自动删除）
     */
    @Bean
    public Queue workQueue() {
        return new Queue(
            AMPQConstant.QUEUE_WORK,    // 队列名称
            true,                         // 持久化 - 确保任务不丢失
            false,                        // 非独占 - 允许多个消费者
            false                         // 非自动删除 - 持久化队列
        );
    }
}