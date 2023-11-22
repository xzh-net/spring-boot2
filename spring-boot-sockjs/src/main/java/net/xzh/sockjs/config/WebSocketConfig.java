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
		registry.addEndpoint("/websocket") // 端点名称
				// .setHandshakeHandler() 握手处理，主要是连接的时候认证获取其他数据验证等
				// .addInterceptors() 拦截处理，和http拦截类似
				.setAllowedOrigins("*") // 跨域
				.withSockJS(); // 使用sockJS
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/**
		 * 这里使用的是内存模式，生产环境可以使用rabbitmq或者其他mq。
		 */
		// 定义了一个（或多个）客户端订阅地址的前缀信息，也就是客户端接收服务端发送消息的前缀信息，这里注册两个，主要是目的是将广播和点对点分开。
		registry.enableSimpleBroker("/topic/", "/user/");
		// 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/，但是必须保证该前缀包含在客户端订阅前缀范围内
		registry.setUserDestinationPrefix("/user/");
		// 定义了服务端接收地址的前缀，即客户端给服务端发消息的地址前缀
		registry.setApplicationDestinationPrefixes("/app");

		// 其他方式
		// registry.enableStompBrokerRelay().setRelayHost().setRelayPort()
	}
}
