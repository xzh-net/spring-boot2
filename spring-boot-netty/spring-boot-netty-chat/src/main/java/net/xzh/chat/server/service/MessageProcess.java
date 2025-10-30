package net.xzh.chat.server.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.xzh.chat.server.enums.ChatProtocol;

/**
 * 消息业务处理器
 * 
 * @date 2020/12/28 10:54
 *
 */
public class MessageProcess {
	private static ChannelGroup onlineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	// channel自定义属性
	private final AttributeKey<Map<String, String>> MAP_ATTR = AttributeKey.valueOf("map");

	@SuppressWarnings("unchecked")
	public void process(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		// 消息处理
		try {
			// 1. 解析JSON消息
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> msgMap = mapper.readValue(msg.text(), Map.class);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			// 2. 添加时间戳和在线人数
			msgMap.put("date", sdf.format(new Date()));
			msgMap.put("total", String.valueOf(onlineUsers.size()));
			String protocolType = msgMap.get("protocolType");

			// 3. 根据协议类型路由处理
			if (ChatProtocol.LOGIN.name().equals(protocolType)) {
				ctx.channel().attr(MAP_ATTR).getAndSet(msgMap);
				loginNotice(ctx, msgMap); // 登录处理
			} else if (ChatProtocol.LOGOUT.name().equals(protocolType)) {
				logoutNotice(ctx, msgMap); // 退出处理
			} else if (ChatProtocol.CHAT.name().equals(protocolType)) {
				groupChat(ctx, msgMap);// 聊天处理
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 聊天处理
	 * 
	 * @param ctx
	 * @param msgMap
	 */
	public void groupChat(ChannelHandlerContext ctx, Map<String, String> msgMap) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		for (Channel channel : onlineUsers) {
			channel.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(msgMap)));
		}
	}

	/**
	 * 登录处理
	 *
	 * @param ctx
	 * @param msgMap
	 */
	public void loginNotice(ChannelHandlerContext ctx, Map<String, String> msgMap) throws JsonProcessingException {
		msgMap.put("protocolType", ChatProtocol.SYSTEM.name());
		msgMap.put("message", "【" + msgMap.get("loginName") + "】已上线");
		msgMap.put("total", String.valueOf(onlineUsers.size()));
		ObjectMapper mapper = new ObjectMapper();
		for (Channel ch : onlineUsers) {
			// if (ctx.channel() != ch) {
			ch.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(msgMap)));
			// }
		}
	}

	/**
	 * 退出登录
	 * 
	 * @param ctx
	 * @param msgMap
	 * @throws JsonProcessingException
	 */
	public void logoutNotice(ChannelHandlerContext ctx, Map<String, String> msgMap) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		msgMap.put("protocolType", ChatProtocol.SYSTEM.name());
		msgMap.put("message", "【" + msgMap.get("loginName") + "】已离线");
		msgMap.put("total", String.valueOf(onlineUsers.size()));
		for (Channel ch : onlineUsers) {
			ch.writeAndFlush(new TextWebSocketFrame(mapper.writeValueAsString(msgMap)));
		}
	}

	/**
	 * 用户上线
	 *
	 * @param ctx
	 */
	public void login(ChannelHandlerContext ctx) {
		onlineUsers.add(ctx.channel());
	}

	/**
	 * 用户下线
	 *
	 * @param ctx
	 */
	public void logout(ChannelHandlerContext ctx) throws JsonProcessingException {
		Map<String, String> map = ctx.channel().attr(MAP_ATTR).get();
		map.put("protocolType", ChatProtocol.LOGOUT.name());
		ObjectMapper mapper = new ObjectMapper();
		process(ctx, new TextWebSocketFrame(mapper.writeValueAsString(map)));
		onlineUsers.remove(ctx.channel());
	}

}
