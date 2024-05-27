package net.xzh.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 路由模式监听器
 */

@Service
public class DirectExchangeListener {

	private static final Logger log = LoggerFactory.getLogger(DirectExchangeListener.class);

	@RabbitListener(queues = "#{directQueue1.name}")
	public void receive1(String message) {
		log.info("{}[error,warn]订阅到了消息,topic={}", CommonConstant.EXCHANGE_DIRECT, message);
	}

	@RabbitListener(queues = "#{directQueue2.name}")
	public void receive2(String message) {
		log.info("{}[info,debug]订阅到了消息,topic={}", CommonConstant.EXCHANGE_DIRECT, message);
	}

}
