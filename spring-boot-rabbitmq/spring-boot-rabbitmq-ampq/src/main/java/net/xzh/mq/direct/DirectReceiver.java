package net.xzh.mq.direct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Created by macro on 2020/5/19.
 */
public class DirectReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectReceiver.class);

	@RabbitListener(queues = "#{directQueue1.name}")
	public void receive1(String message) {
		LOGGER.info("DirectReceiver[error,warn] , {}", message);
	}

	@RabbitListener(queues = "#{directQueue2.name}")
	public void receive2(String message) {
		LOGGER.info("DirectReceiver[info,debug] , {}", message);
	}

}
