package net.xzh.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * MQTT消息发布者配置
 */
@Configuration
public class MqttProducerConfig {

	@Autowired
	private MqttProperties mqttProperties;

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { mqttProperties.getBrokerUrl() });
		options.setUserName(mqttProperties.getUsername());
		options.setPassword(mqttProperties.getPassword().toCharArray());
		options.setKeepAliveInterval(mqttProperties.getKeepAlive());
		options.setAutomaticReconnect(mqttProperties.getReconnect());
		options.setCleanSession(mqttProperties.getCleanSession());
		factory.setConnectionOptions(options);
		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperties.getServerId(),
				mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(mqttProperties.getDefaultTopic());
		return messageHandler;
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}	
}
