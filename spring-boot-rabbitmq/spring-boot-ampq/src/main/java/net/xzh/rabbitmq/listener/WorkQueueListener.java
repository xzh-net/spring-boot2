package net.xzh.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 工作模式监听器
 */

@Service
public class WorkQueueListener {

	private static final Logger log = LoggerFactory.getLogger(WorkQueueListener.class);

	@RabbitListener(queues = CommonConstant.QUEUE_WORK)
	public void receive(String message) {
		log.info("{}订阅到了消息,topic={}", CommonConstant.QUEUE_WORK, message);
	}

}
