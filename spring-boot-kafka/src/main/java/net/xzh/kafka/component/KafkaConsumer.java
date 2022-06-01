package net.xzh.kafka.component;
 
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
 
/**
 * @author xzh
 * @version 2017/9/1 下午05:21
 */
@Component
public class KafkaConsumer {
 
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
 
    /**
     * 监听topic1主题,单条消费
     */
    @KafkaListener(topics = "topic1")
    public void listen0(ConsumerRecord<String, String> record) {
        consumer(record);
    }
    /**
     * 监听topic2,批量消费
     */
    @KafkaListener(topics = "topic2", containerFactory = "batchFactory")
    public void listen2(List<ConsumerRecord<String, String>> records) {
        batchConsumer(records);
    }
 
    /**
     * 单条消费
     */
    public void consumer(ConsumerRecord<String, String> record) {
        logger.info("主题:{}, 内容: {}", record.topic(), record.value());
    }
    
    /**
     * 批量消费
     */
    public void batchConsumer(List<ConsumerRecord<String, String>> records) {
        records.forEach(record -> consumer(record));
    }
}