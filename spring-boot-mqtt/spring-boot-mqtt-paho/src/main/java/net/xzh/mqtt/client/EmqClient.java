package net.xzh.mqtt.client;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.xzh.mqtt.properties.MqttProperties;

/**
 * MQTT客户端
 */
@Component
public class EmqClient {

	private static final Logger log = LoggerFactory.getLogger(MqttClient.class);

	@Autowired
	private MqttProperties mqttProperties;

	@Autowired
	private MessageCallback messageCallback;

	private MqttClient client;

	
	public MqttClient getClient() {
		return client;
	}

	/**
	 * 客户端连接
	 */
	@PostConstruct
	private void init() {
		try {
			client = new MqttClient(mqttProperties.getBrokerUrl(), mqttProperties.getClientId(),
					new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setUserName(mqttProperties.getUsername());
			options.setPassword(mqttProperties.getPassword().toCharArray());
			options.setConnectionTimeout(mqttProperties.getTimeout());
			options.setKeepAliveInterval(mqttProperties.getKeepAlive());
			options.setAutomaticReconnect(mqttProperties.getReconnect());
			options.setCleanSession(mqttProperties.getCleanSession());
			// 设置回调
			client.setCallback(messageCallback);
			client.connect(options);
			if (!client.isConnected()) {
				log.info("mqtt链接{}失败", mqttProperties.getBrokerUrl());
			} else {
				log.info("mqtt链接{}成功", mqttProperties.getBrokerUrl());
			}
		} catch (MqttException e) {
			log.error("MqttAcceptClient connect error,message:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 发布消息
	 *
	 * @param retained 是否保留
	 * @param topic    主题，格式： server:${env}:report:${topic}
	 * @param content  消息内容
	 */
	public void publish(boolean retained, String topic, String content) {
		MqttMessage message = new MqttMessage();
		message.setQos(mqttProperties.getQos());
		message.setRetained(retained);
		message.setPayload(content.getBytes());
		MqttTopic mTopic = client.getTopic(topic);
		MqttDeliveryToken token;
		try {
			token = mTopic.publish(message);
			token.waitForCompletion();
		} catch (MqttException e) {
			log.error("MqttSendClient publish error,message:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 订阅某个主题
	 *
	 * @param topic 主题
	 * @param qos   连接方式
	 */
	public void subscribe(String topic, int qos) {
		log.info("========================【开始订阅主题:" + topic + "】========================");
		try {
			client.subscribe(topic, qos);
		} catch (MqttException e) {
			log.error("MqttAcceptClient subscribe error,message:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 取消订阅某个主题
	 *
	 * @param topic
	 */
	public void unsubscribe(String topic) {
		log.info("========================【取消订阅主题:" + topic + "】========================");
		try {
			client.unsubscribe(topic);
		} catch (MqttException e) {
			log.error("MqttAcceptClient unsubscribe error,message:{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
