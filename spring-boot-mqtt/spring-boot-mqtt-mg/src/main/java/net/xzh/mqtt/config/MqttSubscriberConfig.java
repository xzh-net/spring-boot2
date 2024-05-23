package net.xzh.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * MQTT消息订阅者相关配置
 */
@Configuration
public class MqttSubscriberConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MqttClient.class);
	
	@Autowired
	private MqttProperties mqttProperties;

	@Bean
	public MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	/**
	 * 使用生产者工厂的配置，或者可以使用另外一套订阅者的配置参数
	 * 注意：生产者和订阅者必须使用不同的客户端id
	 */
	@Autowired
	MqttPahoClientFactory mqttClientFactory;
	
	@Bean
	public MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId(),
				mqttClientFactory, mqttProperties.getDefaultTopic());
		adapter.setCompletionTimeout(mqttProperties.getTimeout());
		adapter.setConverter(new DefaultPahoMessageConverter());
		// 设置消息质量：0->至多一次；1->至少一次；2->只有一次
		adapter.setQos(mqttProperties.getQos());
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	public MessageHandler handler() {
		return new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				// 处理订阅消息
				log.info("订阅者订阅到了消息,payload={}", message.getPayload());
			}
		};
	}
}
