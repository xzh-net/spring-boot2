package net.xzh.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.kafka.entity.User;

/**
 * KafkaTemplate测试
 * 
 * @author xzh
 *
 */
@RestController
@Transactional
public class KafkaController {

	private static final Logger log = LoggerFactory.getLogger(KafkaController.class);

	@Autowired
	KafkaTemplate<Object, Object> kafkaTemplate;

	/**
	 * 发送简单消息（系统设置回调）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
	public String sendMessage(@RequestParam String message) {
		kafkaTemplate.send("topic1", message);
		return "消息发送成功";
	}

	/**
	 * 发送带回调的异步消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendMessageWithCallback", method = RequestMethod.GET)
	public String sendMessageWithCallback(@RequestParam String message) {
		ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send("topic2", message);
		future.addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
			@Override
			public void onSuccess(SendResult<Object, Object> result) {
				log.info("【自定义回调】消息发送成功: topic={}, partition={}, offset={}", result.getRecordMetadata().topic(),
						result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("【自定义回调】消息发送失败: {}", ex.getMessage());
			}
		});

		return "异步消息发送成功";
	}

	/**
	 * 发送对象消息（系统设置回调）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendObjectMessage", method = RequestMethod.GET)
	public String sendObjectMessage() {
		User user = new User();
		user.setUserId(100);
		user.setUserName("我们都是好孩子");
		kafkaTemplate.send("topic3", user);
		return "对象发送成功";
	}

	/**
	 * 批量发送简单消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendMultiple", method = RequestMethod.GET)
	public String sendMultiple() {
		for (int i = 1; i <= 24; i++) {
			kafkaTemplate.send("topic4", "发送到Kafka的消息" + i);
		}
		return "批量发送成功";
	}

	/**
	 * 发送分区消息
	 * 根据key的hash值分配到特定分区，相同key的消息分配到同一分区
	 * 日志收集：不需要顺序保证
	 * 行为跟踪，订单状态更新，保证同一订单状态变更顺序。
	 * 没有partition，系统自动管理分区位置
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendPartition", method = RequestMethod.GET)
	public String sendPartition() {
		kafkaTemplate.send("topic5", 5, "key", "我是个大盗贼");
		return "分区发送成功";
	}

}
