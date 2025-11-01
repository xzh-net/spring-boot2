package net.xzh.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

	private static final Logger log = LoggerFactory.getLogger(RabbitMQCallback.class);

	/**
	 * confirm机制只保证消息到达exchange，不保证消息可以路由到正确的queue,如果exchange错误，就会触发confirm机制
	 *
	 * @param correlationData
	 * @param ack
	 * @param cause
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			log.debug("{}-到达路由", correlationData.getId());
		} else {
			log.error("{}-未到达路由,cause:{}", correlationData.getId(), cause);
		}
	}

	/**
	 * Return 消息机制用于处理一个不可路由的消息。在某些情况下，如果我们在发送消息的时候，当前的 exchange 不存在或者指定路由 key
	 * 路由不到，这个时候我们需要监听这种不可达的消息 就需要这种return机制
	 * 
	 * @param message
	 * @param replyCode  错误码
	 * @param replyText  错误信息
	 * @param exchange   交换机
	 * @param routingKey 路由键
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		log.error("消息未达队列,message:{},replyCode:{},replyText:{},exchange:{},routing:{}", message.toString(), replyCode,
				replyText, exchange, routingKey);
	}
}