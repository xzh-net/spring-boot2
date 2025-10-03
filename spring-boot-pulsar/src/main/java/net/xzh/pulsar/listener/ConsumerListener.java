package net.xzh.pulsar.listener;

import org.springframework.stereotype.Component;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import net.xzh.pulsar.dto.MessageDto;

/**
 * 使用注解方式的消费者
 */
@Slf4j
@Component
public class ConsumerListener {

	@PulsarConsumer(topic = "systemTopic", clazz = MessageDto.class)
	public void system(MessageDto message) {
		log.info("系统主题systemTopic接受到，id:{},content:{}", message.getId(), message.getContent());
	}

	@PulsarConsumer(topic = "userTopic", clazz = String.class)
	public void user(String msg) {
		log.info("用户主题userTopic接受到，{}", msg);
	}

}
