package net.xzh.mq.receive;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 点对点接收
 * 
 * @author CR7
 *
 */
@Component
public class QueueReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueueReceiver.class);

	@JmsListener(destination = "queue", containerFactory = "jmsListenerContainerQueue")
	public void receive(Message message, Session session) throws JMSException {
		TextMessage textmessage = (TextMessage)message;
		LOGGER.info("queue 收到消息，{}",textmessage.getText());
		try {
			if (textmessage.getText().contains("error")) {
				throw new JMSException("模拟抛出的异常");
			}
			message.acknowledge();
		} catch (JMSException e) {
			LOGGER.info("异常触发重发机制，msg：{}",textmessage.getText());
			session.recover();
		}
	}
}
