package net.xzh.mqtt.client;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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

	private static final Logger logger = LoggerFactory.getLogger(MqttClient.class);

	@Autowired
	private MqttProperties mqttProperties;

	@Autowired
	private MessageCallback messageCallback;
	
	public static MqttClient client;
	
	public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client) {
    	EmqClient.client = client;
    }

	/**
	 * 客户端连接
	 */
    @PostConstruct
	public void connect() {
		MqttClient client;
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
			EmqClient.setClient(client);
			// 设置回调
			client.setCallback(messageCallback);
			client.connect(options);
		} catch (Exception e) {
			logger.error("MqttAcceptClient connect error,message:{}", e.getMessage());
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
		try {
			client.publish(mqttProperties.getServerTopic(topic), message);
		} catch (MqttException e) {
			logger.error("MqttSendClient publish error,message:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			disconnect(client);
			close(client);
		}
	}
	
	/**
     * 订阅某个主题
     *
     * @param topic 主题
     * @param qos   连接方式
     */
    public void subscribe(String topic, int qos) {
        logger.info("========================【开始订阅主题:" + topic + "】========================");
        try {
        	client.subscribe(topic, qos);
        } catch (MqttException e) {
            logger.error("MqttAcceptClient subscribe error,message:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 取消订阅某个主题
     *
     * @param topic
     */
    public void unsubscribe(String topic) {
        logger.info("========================【取消订阅主题:" + topic + "】========================");
        try {
            client.unsubscribe(topic);
        } catch (MqttException e) {
            logger.error("MqttAcceptClient unsubscribe error,message:{}", e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 重新连接
     */
    public void reconnection() {
        try {
            client.connect();
        } catch (MqttException e) {
            logger.error("MqttAcceptClient reconnection error,message:{}", e.getMessage());
            e.printStackTrace();
        }
    }
    
	/**
	 * 关闭连接
	 *
	 * @param mqttClient
	 */
	public static void disconnect(MqttClient mqttClient) {
		try {
			if (mqttClient != null)
				mqttClient.disconnect();
		} catch (MqttException e) {
			logger.error("MqttSendClient disconnect error,message:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 释放资源
	 *
	 * @param mqttClient
	 */
	public static void close(MqttClient mqttClient) {
		try {
			if (mqttClient != null)
				mqttClient.close();
		} catch (MqttException e) {
			logger.error("MqttSendClient close error,message:{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
