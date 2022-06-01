package net.xzh.kafka.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.xzh.kafka.listener.KafkaSendResultHandler;

@Component
public class KafkaProducer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaSendResultHandler producerListener;

    /**
     * 向topic中发送消息
     */
    public void send (String topic, String msg) {
        kafkaTemplate.send(topic, msg);
    }
    
    /**
     * 向topic中批量发送消息
     */
    public void send (String topic, List<String> msgs) {
        msgs.forEach(msg -> kafkaTemplate.send(topic, msg));
    }
    
    /**
     * 无事务消息
     * @param topic
     * @param msg
     */
    public void sendNoTran(String topic, String msg) {
        kafkaTemplate.setProducerListener(producerListener);
        kafkaTemplate.send(topic, msg);
    }
    
    /**
     * 事务消息
     * @param topic
     * @param msg
     */
    @Transactional
    public void sendByTran(String topic, String msg) {
        kafkaTemplate.setProducerListener(producerListener);
        kafkaTemplate.send(topic, msg);
        int i = 1 / 0;
    }
}
