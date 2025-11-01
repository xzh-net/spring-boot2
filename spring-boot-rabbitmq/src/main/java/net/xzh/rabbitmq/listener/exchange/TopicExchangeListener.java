package net.xzh.rabbitmq.listener.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.common.constant.AMPQConstant;
import net.xzh.rabbitmq.config.exchange.TopicExchangeConfig;

/**
 * 主题模式监听器
 * 优化点：
 * 1. 使用常量引用队列名称
 * 2. 方法命名更具语义化
 * 3. 添加异常处理
 * 4. 提取公共处理逻辑
 * 5. 日志信息更清晰
 */
@Service
public class TopicExchangeListener {

    private static final Logger log = LoggerFactory.getLogger(TopicExchangeListener.class);

    /**
     * 监听主题队列1 - 处理橙色相关和兔子相关的消息
     * 路由模式：[*.orange.*, *.*.rabbit]
     */
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE_1)
    public void handleOrangeAndRabbitMessages(String message) {
        log.info("{} - 队列1[{}|{}]接收到消息: {}",
                AMPQConstant.EXCHANGE_TOPIC,
                TopicExchangeConfig.ROUTING_PATTERN_ORANGE,
                TopicExchangeConfig.ROUTING_PATTERN_RABBIT,
                message);
        processTopicMessage(message, "队列1");
    }

    /**
     * 监听主题队列2 - 处理懒惰相关的消息
     * 路由模式：[lazy.#]
     */
    @RabbitListener(queues = TopicExchangeConfig.TOPIC_QUEUE_2)
    public void handleLazyMessages(String message) {
        log.info("{} - 队列2[{}]接收到消息: {}",
                AMPQConstant.EXCHANGE_TOPIC,
                TopicExchangeConfig.ROUTING_PATTERN_LAZY,
                message);
        processTopicMessage(message, "队列2");
    }

    /**
     * 处理主题交换机消息的公共方法
     * 
     * @param message 消息内容
     * @param queueName 队列标识，用于日志和监控
     */
    private void processTopicMessage(String message, String queueName) {
        try {
            // 记录调试信息
            log.debug("开始在{}处理主题交换机消息: {}", queueName, message);
            
            // 这里实现具体的业务逻辑
            // 根据不同的路由模式，可以有不同的处理逻辑
            
            // 示例：根据队列类型进行不同的业务处理
            switch (queueName) {
                case "队列1":
                    // 处理橙色和兔子相关的业务逻辑
                    // orangeRabbitService.process(message);
                    break;
                case "队列2":
                    // 处理懒惰相关的业务逻辑
                    // lazyService.process(message);
                    break;
                default:
                    // 默认处理逻辑
                    // defaultTopicService.process(message);
                    break;
            }
            
            log.debug("在{}成功处理主题交换机消息", queueName);
            
        } catch (Exception e) {
            log.error("在{}处理主题交换机消息时发生错误, message: {}", queueName, message, e);
            // 可以根据业务需求决定是否抛出异常
            // 对于主题交换机，通常一个队列的处理失败不应该影响其他队列
        }
    }

}