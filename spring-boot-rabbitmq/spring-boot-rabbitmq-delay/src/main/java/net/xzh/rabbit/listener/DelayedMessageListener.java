package net.xzh.rabbit.listener;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 * 延迟插件消息监听器
 * rabbitmq-delayed-message-exchange
 * @author CR7
 *
 */
@Component
public class DelayedMessageListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DelayedMessageListener.class);
	
	// 监听延时插件消息队列
	@RabbitListener(queues = "delay_queue")
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel)
			throws IOException {
		// 如果订单状态不是0 说明订单已经被其他消费队列改动过了 加一个状态用来判断集群状态的情况
		long tag = message.getMessageProperties().getDeliveryTag();
		channel.basicAck(tag, false);
		LOGGER.info("接收到消息：{},{}",tag,new String(message.getBody(), "utf-8"));
	}
}
