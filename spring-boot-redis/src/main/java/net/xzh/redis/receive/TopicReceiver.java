package net.xzh.redis.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * topic订阅接收器
 * @author Administrator
 *
 */
@Component
public class TopicReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicReceiver.class);
	
	/**
	 * 订阅消息
	 * 
	 * @param message
	 */
	public void receiveMessage1(String message) {
		LOGGER.info(" TopicReceiver msg1 ,{}",message);
	}

	public void receiveMessage2(String message) {
		LOGGER.info(" TopicReceiver msg2 ,{}",message);
	}
}