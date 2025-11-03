package net.xzh.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ消息发送回调处理器
 */
@Component
public class RabbitMQCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQCallback.class);

	/**
	 * Confirm机制回调 只保证消息到达Exchange，不保证消息可以路由到正确的Queue
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		String messageId = correlationData != null ? correlationData.getId() : "unknown";
		if (ack) {
			logger.info("✅ 消息发送成功确认 - Message ID: {}", messageId);
		} else {
			logger.error("❌ 消息发送失败确认 - Message ID: {}, Cause: {}", messageId, cause != null ? cause : "未知原因");
		}
	}

	/**
	 * Return消息机制回调 处理不可路由的消息（Exchange存在但RoutingKey路由不到任何Queue）
	 * 
	 */
	@Override
	public void returnedMessage(ReturnedMessage returned) {
		// 记录完整的退回信息
		logger.info("消息退回详情 - Exchange: {}, RoutingKey: {}, ReplyCode: {}, ReplyText: {}, Message: {}",
				returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(),
				returned.getMessage());
	}
}
