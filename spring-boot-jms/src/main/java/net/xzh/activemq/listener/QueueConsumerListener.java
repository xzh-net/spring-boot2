package net.xzh.activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * queue模式的消费者
 * 
 * @author CR7
 *
 */
@Component
public class QueueConsumerListener {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumerListener.class);

	@JmsListener(destination = "queue", containerFactory = "jmsListenerContainerQueue")
	public void receive(Message message, Session session) throws JMSException {
		TextMessage textmessage = (TextMessage) message;
		log.info("queue接受到,{}", textmessage.getText());
		try {
			if (textmessage.getText().contains("error")) {
				throw new JMSException("模拟抛出的异常");
			}
			message.acknowledge();
		} catch (JMSException e) {
			log.info("异常触发重发机制，msg：{}", textmessage.getText());
			session.recover();
		}
	}
}
