package net.xzh.mq.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Created by macro on 2020/5/19.
 */

public class SimpleReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReceiver.class);

	@RabbitListener(queues = "simple.hello")
	public void receive(String message) {
		LOGGER.info("SimpleReceiver , {}", message);
	}

}
