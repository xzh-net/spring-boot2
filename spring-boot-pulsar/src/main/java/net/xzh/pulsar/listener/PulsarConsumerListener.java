package net.xzh.pulsar.listener;

import org.springframework.stereotype.Component;

import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import net.xzh.pulsar.dto.MessageDto;

/**
 * 按主题消费
 */
@Slf4j
@Component
public class PulsarConsumerListener {

    @PulsarConsumer(topic="bootTopic", clazz= MessageDto.class)
    public void consume(MessageDto message) {
        log.info("bootTopic接受到，id:{},content:{}",message.getId(),message.getContent());
    }
    
    
    @PulsarConsumer(topic="stringTopic", clazz= String.class)
    public void consume2(String msg) {
        log.info("stringTopic接受到，{}", msg);
    }

}
