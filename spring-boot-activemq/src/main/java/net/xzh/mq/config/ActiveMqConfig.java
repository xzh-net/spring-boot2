package net.xzh.mq.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * ActiveMQ配置类
 */

@Configuration
public class ActiveMqConfig {

	@Bean
	public RedeliveryPolicy redeliveryPolicy() {
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		// 是否在每次尝试重新发送失败后,增长这个等待时间
		redeliveryPolicy.setUseExponentialBackOff(true);
		// 重发次数,默认为6次 这里设置为10次
		redeliveryPolicy.setMaximumRedeliveries(10);
		// 重发时间间隔,默认为1秒
		redeliveryPolicy.setInitialRedeliveryDelay(1);
		// 第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
		redeliveryPolicy.setBackOffMultiplier(2);
		// 是否避免消息碰撞
		redeliveryPolicy.setUseCollisionAvoidance(false);
		// 设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
		redeliveryPolicy.setMaximumRedeliveryDelay(-1);
		return redeliveryPolicy;
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${spring.activemq.broker-url}") String url,
			RedeliveryPolicy redeliveryPolicy) {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "admin", url);
		activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
		return activeMQConnectionFactory;
	}

	/**
	 * 点对点客户端监听器
	 * queue模式的ListenerContainer
	 * 
	 * @param activeMQConnectionFactory
	 * @return
	 */
	@Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(4);
        return factory;
    }

	/**
	 * 订阅客户端监听器
	 * topic模式的ListenerContainer
	 * 
	 * @param activeMQConnectionFactory
	 * @return
	 */
	
	@Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(activeMQConnectionFactory);
        return factory;
	}
}