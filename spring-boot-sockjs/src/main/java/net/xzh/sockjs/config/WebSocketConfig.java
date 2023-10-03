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
		registry.addEndpoint("/webSocket") // 端点名称
				// .setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
				// .addInterceptors() 拦截处理，和http拦截类似
				.setAllowedOrigins("*") // 跨域
				.withSockJS(); // 使用sockJS
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 这里使用的是内存模式，生产环境可以使用rabbitmq或者其他mq。
		// 这里注册两个，主要是目的是将广播和队列分开。
		// registry.enableStompBrokerRelay().setRelayHost().setRelayPort() 其他方式
		registry.enableSimpleBroker("/topic/", "/queue/");
		// 用户名称前缀p2p
		registry.setUserDestinationPrefix("/queue/");
		// 客户端名称前缀
		registry.setApplicationDestinationPrefixes("/app");
	}
}
