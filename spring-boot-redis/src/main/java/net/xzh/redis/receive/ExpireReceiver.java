package net.xzh.redis.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Key过期监听事件
 */
@Component
public class ExpireReceiver extends KeyExpirationEventMessageListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpireReceiver.class);
	

	public ExpireReceiver(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	/**
	 * 数据失效事件，进行数据处理
	 * 
	 * @param message
	 * @param pattern
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		String msg = new String(message.getBody());
		LOGGER.info("Key过期msg ,{}",msg);
	}
}