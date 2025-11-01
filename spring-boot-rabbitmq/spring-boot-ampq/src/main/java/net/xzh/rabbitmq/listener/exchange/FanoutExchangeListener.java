package net.xzh.rabbitmq.listener.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.common.constant.CommonConstant;
import net.xzh.rabbitmq.config.exchange.FanoutExchangeConfig;

/**
 * 发布订阅模式监听器
 * 优化点：
 * 1. 使用常量引用队列名称
 * 2. 方法命名更具语义化
 * 3. 添加异常处理
 * 4. 提取公共处理逻辑
 */
@Service
public class FanoutExchangeListener {

    private static final Logger log = LoggerFactory.getLogger(FanoutExchangeListener.class);

    /**
     * 监听扇形交换机的队列1
     * 所有发送到扇形交换机的消息都会被所有绑定的队列接收
     */
    @RabbitListener(queues = FanoutExchangeConfig.FANOUT_QUEUE_1)
    public void handleFanoutMessage1(String message) {
        log.info("{} - 队列1接收到广播消息, message={}", CommonConstant.EXCHANGE_FANOUT, message);
        processFanoutMessage(message, "队列1");
    }

    /**
     * 监听扇形交换机的队列2
     * 所有发送到扇形交换机的消息都会被所有绑定的队列接收
     */
    @RabbitListener(queues = FanoutExchangeConfig.FANOUT_QUEUE_2)
    public void handleFanoutMessage2(String message) {
        log.info("{} - 队列2接收到广播消息, message={}", CommonConstant.EXCHANGE_FANOUT, message);
        processFanoutMessage(message, "队列2");
    }

    /**
     * 处理扇形交换机消息的公共方法
     * 
     * @param message 消息内容
     * @param queueName 队列标识，用于日志和监控
     */
    private void processFanoutMessage(String message, String queueName) {
        try {
            // 记录调试信息
            log.debug("开始在{}处理扇形交换机消息: {}", queueName, message);
            
            // 这里实现具体的业务逻辑
            // 例如：消息处理、数据存储、通知其他服务等
            
            // 示例业务逻辑
            // if (someCondition) {
            //     businessService.processMessage(message);
            // }
            
            log.debug("在{}成功处理扇形交换机消息", queueName);
            
        } catch (Exception e) {
            log.error("在{}处理扇形交换机消息时发生错误, message: {}", queueName, message, e);
            // 可以根据业务需求决定是否抛出异常
            // 对于扇形交换机，通常一个队列的处理失败不应该影响其他队列
        }
    }
    
}