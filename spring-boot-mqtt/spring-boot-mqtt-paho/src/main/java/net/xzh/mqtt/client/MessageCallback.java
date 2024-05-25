package net.xzh.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.xzh.mqtt.properties.MqttProperties;

/**
 * MQTT接受服务的回调类
 * 
 * @author admin
 *
 */
@Component
public class MessageCallback implements MqttCallbackExtended {

	private static final Logger log = LoggerFactory.getLogger(MessageCallback.class);

	@Autowired
	private EmqClient emqClient;

	@Autowired
	private MqttProperties mqttProperties;

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 客户端收到消息触发
	 *
	 * @param topic   主题
	 * @param message 消息
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.info("订阅者订阅到了消息,topic={},messageid={},qos={},payload={}", topic, message.getId(), message.getQos(),
				new String(message.getPayload()));
		// int i = 1/0;
	}

	/**
	 * 消息发布者消息发布完成产生的回调
	 * 
	 * @param token
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		int messageId = token.getMessageId();
		String[] topics = token.getTopics();
		log.info("发布消息,messageid={},topics={}", messageId, topics);
	}

	/**
	 * 连接emq服务器后触发
	 */
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		log.info("客户端{}连接成功！", emqClient.getClient().getClientId());
		// 以/#结尾表示订阅所有以testtopic开头的主题
		emqClient.subscribe(mqttProperties.getDefaultTopic(), mqttProperties.getQos());
	}

}
