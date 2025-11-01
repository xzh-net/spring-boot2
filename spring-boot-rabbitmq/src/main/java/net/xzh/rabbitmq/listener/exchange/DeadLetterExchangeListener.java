package net.xzh.rabbitmq.listener.exchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import net.xzh.rabbitmq.config.exchange.DeadLetterExchangeConfig;

/**
 * 死信队列监听器
 * 
 * 死信队列典型应用场景：
 * 1. 订单超时取消：订单创建后发送延迟消息，到期检查订单状态
 * 2. 支付超时处理：发送支付消息，超时未支付则取消订单
 * 3. 消息重试机制：处理失败的消息进入死信队列，延迟重试
 * 4. 定时任务：基于消息的延迟执行定时任务
 * 
 */
@Component
public class DeadLetterExchangeListener {

    private static final Logger log = LoggerFactory.getLogger(DeadLetterExchangeListener.class);

    /**
     * 监听死信接收队列 - 处理延迟到期的消息
     * 
     * 使用手动确认模式，确保消息处理成功后才确认
     * 如果处理失败可以选择重新入队或进入死信队列的死信队列
     * 
     * @param message RabbitMQ消息对象
     * @param headers 消息头信息
     * @param channel RabbitMQ通道
     * @throws IOException 通道操作异常
     */
    @RabbitListener(queues = DeadLetterExchangeConfig.DEAD_LETTER_RECEIVE_QUEUE)
    public void handleDeadLetterMessage(Message message, @Headers org.springframework.messaging.MessageHeaders headers, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            log.info("死信队列接收到延迟消息, deliveryTag: {}, message: {}", deliveryTag, messageBody);
            // 处理死信消息
            processDeadLetterMessage(messageBody, headers);
            // 消息处理成功，手动确认
            channel.basicAck(deliveryTag, false);
            log.info("死信消息处理完成并确认, deliveryTag: {}", deliveryTag);
            
        } catch (Exception e) {
            log.error("处理死信消息时发生错误, deliveryTag: {}, message: {}", deliveryTag, messageBody, e);
            
            // 根据异常类型决定处理策略
            handleProcessingFailure(message, deliveryTag, channel, e);
        }
    }

    /**
     * 处理死信消息的核心业务逻辑
     * 
     * @param messageBody 消息内容
     * @param headers 消息头，包含原始的路由键、交换机等信息
     */
    private void processDeadLetterMessage(String messageBody, org.springframework.messaging.MessageHeaders headers) {
        // 记录原始消息信息（从headers中获取）
        String originalExchange = (String) headers.get("x-first-death-exchange");
        String originalRoutingKey = (String) headers.get("x-first-death-reason");
        String deathReason = (String) headers.get("x-first-death-reason");
        
        log.info("死信消息原始信息 - 交换机: {}, 路由键: {}, 死亡原因: {}", 
                 originalExchange, originalRoutingKey, deathReason);
        
        // 根据业务需求处理消息
        // 示例：订单超时处理
        if (messageBody.contains("order_timeout")) {
            processOrderTimeout(messageBody);
        } 
        // 示例：支付超时处理
        else if (messageBody.contains("payment_timeout")) {
            processPaymentTimeout(messageBody);
        }
        // 示例：重试消息处理
        else if (messageBody.contains("retry")) {
            processRetryMessage(messageBody);
        }
        // 默认处理逻辑
        else {
            processDefaultDeadLetter(messageBody);
        }
    }

    /**
     * 处理订单超时逻辑
     */
    private void processOrderTimeout(String messageBody) {
        log.info("处理订单超时业务: {}", messageBody);
        
        try {
            // 提取订单ID
            // String orderId = extractOrderId(messageBody);
            
            // 检查订单状态
            // OrderStatus status = orderService.getOrderStatus(orderId);
            
            // 如果订单仍为待支付状态，则取消订单
            // if (status == OrderStatus.PENDING_PAYMENT) {
            //     orderService.cancelOrder(orderId, "超时自动取消");
            //     log.info("订单超时自动取消: {}", orderId);
            // } else {
            //     log.info("订单状态已变更，无需处理: {}", orderId);
            // }
            
            // 模拟业务处理
            Thread.sleep(100);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("订单超时处理被中断", e);
        } catch (Exception e) {
            log.error("处理订单超时时发生错误: {}", messageBody, e);
            throw new RuntimeException("订单超时处理失败", e);
        }
    }

    /**
     * 处理支付超时逻辑
     */
    private void processPaymentTimeout(String messageBody) {
        log.info("处理支付超时业务: {}", messageBody);
        
        // 支付超时处理逻辑
        // 1. 检查支付状态
        // 2. 如果未支付，取消相关业务
        // 3. 释放库存等资源
        
        // paymentService.handlePaymentTimeout(messageBody);
    }

    /**
     * 处理重试消息
     */
    private void processRetryMessage(String messageBody) {
        log.info("处理重试消息: {}", messageBody);
        
        // 重试消息处理逻辑
        // 1. 解析重试次数
        // 2. 执行重试逻辑
        // 3. 判断是否达到最大重试次数
        
        // retryService.processRetry(messageBody);
    }

    /**
     * 默认死信消息处理
     */
    private void processDefaultDeadLetter(String messageBody) {
        log.info("处理默认死信消息: {}", messageBody);
        
        // 默认的死信消息处理逻辑
        // 记录日志、通知管理员等
        
        // deadLetterDefaultService.process(messageBody);
    }

    /**
     * 处理消息处理失败的情况
     * 
     * @param message 原始消息
     * @param deliveryTag 消息标签
     * @param channel RabbitMQ通道
     * @param exception 异常信息
     * @throws IOException 通道操作异常
     */
    private void handleProcessingFailure(Message message, long deliveryTag, Channel channel, Exception exception) throws IOException {
        // 获取重试次数
        Integer retryCount = message.getMessageProperties().getHeader("x-retry-count");
        if (retryCount == null) {
            retryCount = 0;
        }
        
        // 判断是否达到最大重试次数
        if (retryCount < 3) {
            // 未达到最大重试次数，重新入队
            log.warn("消息处理失败，准备重试，当前重试次数: {}", retryCount);
            
            // 设置重试次数
            message.getMessageProperties().setHeader("x-retry-count", retryCount + 1);
            
            // 拒绝消息并重新入队
            channel.basicNack(deliveryTag, false, true);
        } else {
            // 达到最大重试次数，进入死信队列的死信队列或记录错误日志
            log.error("消息达到最大重试次数，进入最终失败处理，重试次数: {}", retryCount);
            
            // 确认消息，不再重试
            channel.basicAck(deliveryTag, false);
            
            // 记录最终失败的消息
            // failedMessageService.recordFinalFailure(message, exception);
            
            // 或者可以发送到另一个专门的最终失败队列
            // channel.basicPublish("final.failure.exchange", "final.failure.key", null, message.getBody());
        }
    }

}