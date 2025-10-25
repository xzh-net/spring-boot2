package net.xzh.kafka.handler;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 消息发送回调
 */
@Component
public class KafkaResultHandler implements ProducerListener<Object, Object> {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaResultHandler.class);
	
    @Override
    public void onSuccess(ProducerRecord<Object, Object> producerRecord, RecordMetadata recordMetadata) {
        log.info("【系统回调】发送成功 ,{}",producerRecord.toString());
    }

	@Override
    public void onError(ProducerRecord<Object, Object> producerRecord, @Nullable RecordMetadata recordMetadata, Exception exception) {
    	log.info("【系统回调】发送失败 ,{}",producerRecord.toString());
    }
}




  
