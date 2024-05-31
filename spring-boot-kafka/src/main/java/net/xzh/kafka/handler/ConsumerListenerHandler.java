package net.xzh.kafka.handler;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author xzh
 * @version 2024/5/31
 */
@Component
public class ConsumerListenerHandler {

	private static final Logger log = LoggerFactory.getLogger(ConsumerListenerHandler.class);

	/**
	 * 监听topic1主题,单条消费
	 */
	@KafkaListener(topics = "topic1")
	public void listen0(ConsumerRecord<String, String> record, Acknowledgment ack) {
		try {
			// 用于测试异常处理
//			int i = 1 / 0;
			ack.acknowledge(); // 手动确认
			log.info("主题:{}, 内容: {}", record.topic(), record.value());
		} catch (Exception e) {
			log.info("listen0消费失败:{}", e);
		}
	}

	/**
	 * 监听topic2,批量消费
	 */
	@KafkaListener(topics = "topic2", containerFactory = "batchFactory")
	public void listen2(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
		log.info("主题:topic2, 记录数: {}", records.size());
		records.forEach(record -> log.info("内容: {}", record.value()));
		ack.acknowledge(); // 手动确认
	}
}