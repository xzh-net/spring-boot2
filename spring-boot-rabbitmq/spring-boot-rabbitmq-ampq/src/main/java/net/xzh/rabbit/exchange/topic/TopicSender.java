package net.xzh.rabbit.exchange.topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by macro on 2020/5/19.
 */
public class TopicSender {

	@Autowired
	private RabbitTemplate template;

	private static final String exchangeName = "exchange.topic";

	public void send(String message) {
		String key = message;
		template.convertAndSend(exchangeName, key, message);
	}

}
