package net.xzh.sse.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class SseConnectionManager {

	private static final Logger log = LoggerFactory.getLogger(SseConnectionManager.class);

	/**
	 * 当前连接数
	 */
	private static AtomicInteger count = new AtomicInteger(0);

	private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

	public static SseEmitter connect(String userId) {
		// 设置超时日期，0表示不过期
		SseEmitter sseEmitter = new SseEmitter(0L);
		sseEmitter.onCompletion(completionCallBack(userId));
		sseEmitter.onError(errorCallBack(userId));
		sseEmitter.onTimeout(timeoutCallBack(userId));
		sseEmitterMap.put(userId, sseEmitter);
		count.getAndIncrement();
		log.info("创建新连接:{},现有连接用户：{}", userId, sseEmitterMap.keySet());
		try {
			// 注册成功返回用户信息
			sseEmitter.send(SseEmitter.event().id(userId).name("connecttime").data(userId, MediaType.APPLICATION_JSON));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sseEmitter;
	}

	/**
	 * 给指定用户发信息
	 */
	public static void sendMessage(String userId, String message) {
		if (!sseEmitterMap.containsKey(userId)) {
			connect(userId);
		}
		try {
			sseEmitterMap.get(userId).send(message);
			log.info("给" + userId + "号发送消息：" + message);
		} catch (IOException e) {
			removeUser(userId);
			log.error("userId:{},发送信息出错:{}", userId, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 群发消息
	 */
	public static void batchSendMessage(String message) {
		if (sseEmitterMap != null && !sseEmitterMap.isEmpty()) {
			sseEmitterMap.forEach((k, v) -> {
				try {
					v.send(message, MediaType.APPLICATION_JSON);
				} catch (IOException e) {
					removeUser(k);
					log.error("userId:{},发送群信息出错:{}", k, e.getMessage());
					e.printStackTrace();
				}
			});
		}
	}

	public static void batchSendMessage(Set<String> userIds, String message) {
		userIds.forEach(userId -> sendMessage(userId, message));
	}

	private static Runnable completionCallBack(String userId) {
		return () -> {
			log.info("结束连接,{}", userId);
			removeUser(userId);
		};
	}

	private static Runnable timeoutCallBack(String userId) {
		return () -> {
			log.info("连接超时,{}", userId);
			removeUser(userId);
		};
	}

	private static Consumer<Throwable> errorCallBack(String userId) {
		return throwable -> {
			log.error("连接异常,{}", userId);
			removeUser(userId);
		};
	}
	
	/**
	 * 移出用户
	 */
	public static void removeUser(String userId) {
		sseEmitterMap.remove(userId);
		count.getAndDecrement();
		log.info("remove user id:{}", userId);
		log.info("remain user id:" + sseEmitterMap.keySet());
	}

	public static List<String> getIds() {
		return new ArrayList<>(sseEmitterMap.keySet());
	}

	public static int getUserCount() {
		return count.intValue();
	}

}
