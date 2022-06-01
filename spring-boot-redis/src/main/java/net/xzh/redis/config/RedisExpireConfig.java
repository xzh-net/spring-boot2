package net.xzh.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import net.xzh.redis.receive.GlobalExpireListener;
import net.xzh.redis.receive.TopicReceiver;

/**
 * 失效监听
 * 
 * @author Administrator
 *
 */
@Configuration
public class RedisExpireConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisExpireConfig.class);

	/**
	 * redis消息监听器容器 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * 
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return
	 */
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter, MessageListenerAdapter listenerAdapter2) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// 可以添加多个主题
		container.addMessageListener(listenerAdapter, new PatternTopic("redisChat"));
		container.addMessageListener(listenerAdapter2, new PatternTopic("redisChat2"));
		// 绑定自定义全局失效监听
		container.addMessageListener(new GlobalExpireListener(), new PatternTopic("__keyevent@2__:expired"));

		return container;
	}

	/**
	 * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
	 * 
	 * @param redisReceiver
	 * @return
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(TopicReceiver redisReceiver) {
		LOGGER.info("MessageListenerAdapter1 Loading complete,{}",redisReceiver);
		return new MessageListenerAdapter(redisReceiver, "receiveMessage1");
	}

	@Bean
	MessageListenerAdapter listenerAdapter2(TopicReceiver redisReceiver) {
		LOGGER.info("MessageListenerAdapter2 Loading complete,{}",redisReceiver);
		return new MessageListenerAdapter(redisReceiver, "receiveMessage2");
	}
}