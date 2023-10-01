package net.xzh.websocket.service;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import net.xzh.websocket.domain.Message;

@ServerEndpoint(value = "/chat/{token}")
@Component
public class WebSocketServer {

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static AtomicInteger onlineNum = new AtomicInteger();

	// concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
	private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

	// 建立连接成功调用
	@OnOpen
	public void onOpen(Session session, @PathParam("token") String userName) {
		sessionPools.put(userName, session);
		addOnlineCount();
		System.out.println(userName + "进入房间！当前人数为" + onlineNum);
		// 广播上线消息
		Message msg = new Message();
		msg.setSentTime(System.currentTimeMillis());
		msg.setType(Message.Type.broadcast);
		msg.setDataType(Message.DataType.online);
		msg.setFrom(userName);
		msg.setBody(userName + "进入房间！当前人数为" + onlineNum);
		broadcast(JSON.toJSONString(msg, true));
	}

	// 关闭连接时调用
	@OnClose
	public void onClose(Session session, @PathParam("token") String userName) {
		sessionPools.remove(userName);
		subOnlineCount();
		System.out.println(userName + "离开房间！当前人数为" + onlineNum);
		// 广播下线消息
		Message msg = new Message();
		msg.setSentTime(System.currentTimeMillis());
		msg.setType(Message.Type.broadcast);
		msg.setDataType(Message.DataType.offline);
		msg.setFrom(userName);
		msg.setBody(userName + "离开房间！当前人数为" + onlineNum);
		broadcast(JSON.toJSONString(msg, true));
	}

	// 错误时调用
	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace();
	}

	// 收到客户端信息后，根据接收人的username把消息推下去或者群发
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("收到数据：" + message);
		Message msg = JSON.parseObject(message, Message.class);
		msg.setSentTime(System.currentTimeMillis());
		if(msg.type.equals(Message.Type.broadcast)) {
			broadcast(JSON.toJSONString(msg, true));
		} else {
			sendInfo(msg.getTo(), JSON.toJSONString(msg, true));
		}
	}

	// 群发消息
	public void broadcast(String message) {
		for (Session session : sessionPools.values()) {
			try {
				sendMessage(session, message);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	// 发送消息
	public void sendMessage(Session session, String message) throws IOException {
		if (session != null) {
			synchronized (session) {
				System.out.println("发送数据：" + message);
				session.getBasicRemote().sendText(message);
			}
		}
	}

	// 给指定用户发送信息
	public void sendInfo(String userName, String message) {
		Session session = sessionPools.get(userName);
		try {
			sendMessage(session, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addOnlineCount() {
		onlineNum.incrementAndGet();
	}

	public static void subOnlineCount() {
		onlineNum.decrementAndGet();
	}
}
