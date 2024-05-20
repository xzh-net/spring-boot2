package net.xzh.mqtt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT配置信息
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

	private String brokerUrl;

	private String clientId;

	private String username;

	private String password;
	
	/**
     * 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端
     * 发送个消息判断客户端是否在线，但这个方法并没有重连的机制
     */
    private int keepAlive;
    
	/**
     * 超时时间
     */
    private int timeout;
    
	/**
	 * 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连 接记录，这里设置为true表示每次连接到服务器都以新的身份连接
	 */
	private Boolean cleanSession;

	/**
	 * 是否断线重连
	 */
	private Boolean reconnect;

	/**
	 * 连接方式
	 */
	private Integer qos;
	
	/**
     * 默认连接主题
     */
    private String  defaultTopic;


	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(int keepAlive) {
		this.keepAlive = keepAlive;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Boolean getCleanSession() {
		return cleanSession;
	}

	public void setCleanSession(Boolean cleanSession) {
		this.cleanSession = cleanSession;
	}

	public Boolean getReconnect() {
		return reconnect;
	}

	public void setReconnect(Boolean reconnect) {
		this.reconnect = reconnect;
	}

	public Integer getQos() {
		return qos;
	}

	public void setQos(Integer qos) {
		this.qos = qos;
	}

	public String getDefaultTopic() {
        return defaultTopic + "/#";
    }

	public void setDefaultTopic(String defaultTopic) {
		this.defaultTopic = defaultTopic;
	}
}
