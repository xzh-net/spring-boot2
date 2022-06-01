package net.xzh.mq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 主題接受器
 * @author CR7
 *
 */
@Component
public class TopicReceiver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TopicReceiver.class);

	 @JmsListener(destination = "topic", containerFactory = "jmsListenerContainerTopic")
     public void receive(String msg) {
		 LOGGER.info("topic 收到消息，{}",msg);
    }
}
