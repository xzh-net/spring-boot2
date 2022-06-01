package net.xzh.mq.receive;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "delay-topic", topic = "delay-topic", selectorExpression = "*")
public class DelayBindListener implements RocketMQListener<String> {

	private static final Logger log = LoggerFactory.getLogger(DelayBindListener.class);
	
	@Override
	public void onMessage(String message) {
		log.info("接收到产品信息:{},消费时间:{}", message, System.currentTimeMillis());
	}
}