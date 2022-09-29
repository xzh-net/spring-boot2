package net.xzh.rabbit.exchange.work;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by macro on 2020/5/19.
 */
public class WorkSender {

	@Autowired
	private RabbitTemplate template;

	private static final String queueName = "work.hello";

	public void send(String message) {
		template.convertAndSend(queueName, message);
	}

}
