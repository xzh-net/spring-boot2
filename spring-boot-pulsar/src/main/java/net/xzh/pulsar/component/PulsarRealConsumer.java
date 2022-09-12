package net.xzh.pulsar.component;

import org.springframework.stereotype.Component;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import net.xzh.pulsar.dto.MessageDto;

/**
 * Pulsar消息消费者
 */
@Slf4j
@Component
public class PulsarRealConsumer {

    @PulsarConsumer(topic="bootTopic", clazz= MessageDto.class)
    public void consume(MessageDto message) {
        log.info("PulsarRealConsumer consume id:{},content:{}",message.getId(),message.getContent());
    }

}
