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
 * 消费监听器
 * 
 * @author CR7
 *
 */
@Component
public class ManualListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManualListener.class);

	@RabbitListener(queues = "item_queue")
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) {
		String messageId = message.getMessageProperties().getMessageId();
		long tag = message.getMessageProperties().getDeliveryTag();
		try {
			if (messageId.endsWith("1")) {
				channel.basicAck(tag, false);// 手动签收消息,通知mq服务器端删除该消息
				LOGGER.info("接收到消息：{},{}", tag, message);
			} else if (messageId.endsWith("2")) {
				channel.basicNack(tag, false, true);// 重回队列，根据实际应用
				LOGGER.info("消息重回队列：{},{}", tag, message);
			} else {
				int i = 1 / 0;
				LOGGER.info("模拟异常消息：{},{}", tag, message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				channel.basicNack(tag, false, false);// 拒绝接收，丢弃该消息 配合手动处理和数据库日志
				LOGGER.info("丢弃消息：{},{}", tag, message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
}