package net.xzh.kafka.listener;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import net.xzh.kafka.entity.User;

/**
 * 消费者服务监听
 * 
 * @author xzh
 * @version 2024/5/31
 */
@Component
public class ConsumerListener {

	private static final Logger log = LoggerFactory.getLogger(ConsumerListener.class);

	/**
	 * 消费简单消息，设置自动提交工厂
	 */
	@KafkaListener(topics = "topic1", groupId = "my-group", containerFactory = "autoCommitFactory")
	public void topic1(String message) {
		log.info("收到简单消息: {}", message);
	}

	/**
	 * 消费简单消息，未设置提交工厂，走系统默认，但是系统没有设置提交，此类消息重启后会重新推送 带异步回调
	 * 
	 * @param message
	 */
	@KafkaListener(topics = "topic2", groupId = "my-group")
	public void topic2(String message, Acknowledgment ack,
			@Header(name = "retry-count", required = false) Integer retryCount) {
		//系统没设置自动提交，业务也没有手动提交，抛出异常，消息会重新投递，根据重试次数扩展 
		log.info("收到简单消息2: {}", message);

	}

	/**
	 * 消费对象消息，未设置提交工厂，走系统默认，但是手动提交了
	 * 
	 * @param user
	 */
	@KafkaListener(topics = "topic3", groupId = "my-group")
	public void topic3(User user, Acknowledgment ack) {
		log.info("收到用户对象: {}", user);
		ack.acknowledge(); // 手动确认
	}

	/**
	 * 消费批量简单消息，手动提交
	 */
	@KafkaListener(topics = "topic4", groupId = "my-group", containerFactory = "batchFactory")
	public void topic4(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
		log.info("收到批量消息: {}", records.size());
		records.forEach(record -> log.info("批量消息: {}", record.value()));
		ack.acknowledge(); // 手动确认
	}
}