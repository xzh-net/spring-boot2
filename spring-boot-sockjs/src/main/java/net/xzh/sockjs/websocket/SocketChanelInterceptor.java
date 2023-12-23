package net.xzh.sockjs.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import net.xzh.sockjs.common.util.JwtTokenUtil;
import net.xzh.sockjs.component.DynamicSecurityMetadataSource;
import net.xzh.sockjs.services.UmsAdminService;

/**
 * 功能:频道拦截器,类似管道,获取消息的一些meta数据
 */
@Component
public class SocketChanelInterceptor implements ChannelInterceptor {

	@Autowired
	public JwtTokenUtil jwtTokenUtil;

	@Autowired
	public UserDetailsService userDetailsService;

	@Value("${jwt.tokenHead}")
	public String tokenHead;

	@Autowired
	UmsAdminService umsAdminService;

	@Autowired
	DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

	/**
	 * 实际消息发送到频道之前调用
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String jwtToken = accessor.getFirstNativeHeader("token");
			if (jwtToken == null || jwtToken.length() < tokenHead.length()) {
				throw new IllegalArgumentException("抱歉，您没有访问权限");
			}
			jwtToken = jwtToken.substring(tokenHead.length());
			String username = jwtTokenUtil.getUserNameFromToken(jwtToken);
			if (username != null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
					accessor.setUser(authentication);
				}
			} else {
				throw new IllegalArgumentException("抱歉，您没有访问权限");
			}
		}

		return message;
	}

	/**
	 * 消息发送立即调用
	 */
	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
	}

	/**
	 * 消息发送之后进行调用,是否有异常,进行数据清理
	 */
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
		return;
	}
}
