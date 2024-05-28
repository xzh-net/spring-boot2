package net.xzh.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 主题模式监听器
 */
@Service
public class TopicExchangeListener {

	private static final Logger log = LoggerFactory.getLogger(TopicExchangeListener.class);

	@RabbitListener(queues = "#{topicQueue1}")
	public void receive1(String message) {
		log.info("{}[*.orange.*,*.*.rabbit]订阅到了消息,topic={}", CommonConstant.EXCHANGE_TOPIC, message);
	}

	@RabbitListener(queues = "#{topicQueue2}")
	public void receive2(String message) {
		log.info("{}[lazy.#]订阅到了消息,topic={}", CommonConstant.EXCHANGE_TOPIC, message);
	}

}
