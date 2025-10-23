package net.xzh.sockjs.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Value("${rabbitmq.relayHost}")
    private String relayhost;
	@Value("${rabbitmq.relayPort}")
	private int relayport;
	@Value("${rabbitmq.virtualHost}")
	private String virtualhost;
	@Value("${rabbitmq.login}")
	private String login;
	@Value("${rabbitmq.passcode}")
	private String passcode;
	
    @Autowired
    SocketChanelInterceptor socketChanelInterceptor;

    /**
     * 注册stomp的端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //客户端连接端点
    	registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")  // 使用 allowedOriginPatterns
        .withSockJS();
    }

    /**
     * 配置信息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic/","/queue/")
                .setRelayHost(relayhost)
                .setRelayPort(relayport)
                .setClientLogin(login)
                .setClientPasscode(passcode)
                .setVirtualHost(virtualhost);
        registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * 配置客户端入站通道拦截器，用于传递从WebSocket客户端接收到的消息
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(socketChanelInterceptor);
    }

    /**
     * 配置客户端出站通道拦截器，用于向WebSocket客户端发送服务器消息
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        //registration.interceptors(socketChanelInterceptor);
    }
}
