package net.xzh.sockjs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * 注册stomp端点
	 * 
	 * @param registry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 客户端连接端点
		registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
	}

	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic/","/queue/")
                .setRelayHost("172.17.17.161")
                .setRelayPort(61613)
                .setClientLogin("admin")
                .setClientPasscode("123456")
                .setVirtualHost("/");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
