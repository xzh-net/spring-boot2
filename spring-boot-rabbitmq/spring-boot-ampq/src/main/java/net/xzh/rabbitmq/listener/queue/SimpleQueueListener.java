package net.xzh.rabbitmq.listener.queue;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;

import net.xzh.rabbitmq.common.constant.CommonConstant;

/**
 * 简单工作队列模式监听器
 */
@Service
public class SimpleQueueListener {

    private static final Logger log = LoggerFactory.getLogger(SimpleQueueListener.class);

    /**
     * 监听简单工作队列
     * 注意：在简单队列模式下，如果有多个消费者实例，消息会在消费者之间轮询分发
     * 
     * @param message 接收到的消息内容
     */
    @RabbitListener(queues = CommonConstant.QUEUE_SIMPLE)
    public void handleSimpleMessage(String message) {
        long startTime = System.currentTimeMillis();
        
        try {
            log.info("简单队列接收到消息, 开始处理: message={}", message);
            
            // 处理消息
            processMessage(message);
            
            long processingTime = System.currentTimeMillis() - startTime;
            log.info("简单队列消息处理完成, 耗时: {}ms, message={}", processingTime, message);
            
        } catch (Exception e) {
            long failureTime = System.currentTimeMillis() - startTime;
            log.error("简单队列消息处理失败, 耗时: {}ms, message: {}", failureTime, message, e);
            
            // 根据业务需求决定处理方式：
            // 1. 抛出异常让消息重新入队（需要配置重试机制）
            // 2. 记录错误日志但消费成功（可能会丢失消息）
            // 3. 发送到死信队列进行特殊处理
            
            // 示例：对于可重试的异常，可以选择抛出
            // throw new AmqpRejectAndDontRequeueException("处理失败，拒绝消息", e);
        }
    }

    /**
     * 处理消息的核心业务逻辑
     * 
     * @param message 消息内容
     */
    private void processMessage(String message) {
        // 这里实现具体的业务逻辑
        // 例如：数据验证、业务处理、数据库操作等
        
        // 示例业务逻辑：
        // 1. 验证消息格式
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("消息内容不能为空");
        }
        
        // 2. 业务处理
        // yourBusinessService.process(message);
        
        // 3. 模拟处理耗时
        try {
            // 根据消息复杂度模拟不同的处理时间
            if (message.length() > 100) {
                Thread.sleep(100); // 模拟耗时操作
            } else {
                Thread.sleep(50);  // 模拟快速操作
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("处理被中断", e);
        }
        
        log.debug("消息处理完成: {}", message);
    }


    /**
     * 如果需要手动确认消息，可以使用以下方式：
     */
//     @RabbitListener(queues = CommonConstant.QUEUE_SIMPLE)
     public void handleMessageWithAck(String message, Channel channel, 
                                     @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
         try {
             // 处理消息
             processMessage(message);
             
             // 手动确认消息
             channel.basicAck(deliveryTag, false);
            
              log.info("消息处理成功并确认: {}", message);
             
         } catch (Exception e) {
              log.error("消息处理失败: {}", message, e);
             // 拒绝消息，可以选择重新入队或直接丢弃
             try {
                 channel.basicNack(deliveryTag, false, true); // 重新入队
                 
                 // channel.basicNack(deliveryTag, false, false); // 直接丢弃
             } catch (IOException ioException) {
                 log.error("拒绝消息时发生IO异常", ioException);
             }
          }
      }
}