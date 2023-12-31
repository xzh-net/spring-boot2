package net.xzh.redis.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 自定义全局监听失效事件
 * @author Administrator
 *
 */
public class GlobalExpireListener implements MessageListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExpireListener.class);
 
    @Override
    public void onMessage(Message message, byte[] pattern) {
         String msg = new String(message.getBody());
         LOGGER.info("全局失效拦截msg ,{}",msg);
    }
 
}