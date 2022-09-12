package net.xzh.pulsar.component;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import net.xzh.pulsar.dto.MessageDto;

/**
 * Pulsar消息生产者
 */
@Component
public class PulsarProducer {
    @Autowired
    private PulsarTemplate<MessageDto> template;

    public void send(MessageDto message){
        try {
            template.send("bootTopic",message);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
