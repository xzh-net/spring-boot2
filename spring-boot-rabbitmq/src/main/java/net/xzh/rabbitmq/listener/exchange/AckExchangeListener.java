package net.xzh.rabbitmq.listener.exchange;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import net.xzh.rabbitmq.config.exchange.AcknowledgeExchangeConfig;

/**
 * ACK有回执的监听器
 */
@Service
public class AckExchangeListener {

    private static final Logger log = LoggerFactory.getLogger(AckExchangeListener.class);

    @RabbitListener(queues = AcknowledgeExchangeConfig.ACK_QUEUE)
    public void handleAckMessages(Message message, @Headers org.springframework.messaging.MessageHeaders headers, Channel channel) {
    	 long deliveryTag = message.getMessageProperties().getDeliveryTag();
         String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
         try {
             log.info("有回执的队列接收到消息, deliveryTag: {}, message: {}", deliveryTag, messageBody);
             log.info("有回执的队列处理完成并确认, deliveryTag: {}", deliveryTag);
             
         } catch (Exception e) {
             log.error("处理死信消息时发生错误, deliveryTag: {}, message: {}", deliveryTag, messageBody, e);
             
         }
    }

}