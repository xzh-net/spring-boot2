package net.xzh.mq.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * topic模式的消费者
 * @author CR7
 *
 */
@Component
public class TopicConsumerListener {
	
	private static final Logger log = LoggerFactory.getLogger(TopicConsumerListener.class);

	 @JmsListener(destination = "topic", containerFactory = "jmsListenerContainerTopic")
     public void receive(String msg) {
		 log.info("topic接受到，{}",msg);
    }
}
