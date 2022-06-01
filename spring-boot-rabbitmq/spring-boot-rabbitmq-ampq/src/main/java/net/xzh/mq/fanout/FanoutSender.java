package net.xzh.mq.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by macro on 2020/5/19.
 */
public class FanoutSender {
	@Autowired
	private RabbitTemplate template;

	private static final String exchangeName = "exchange.fanout";

	public void send(String message) {
		template.convertAndSend(exchangeName, "", message);
	}

}
