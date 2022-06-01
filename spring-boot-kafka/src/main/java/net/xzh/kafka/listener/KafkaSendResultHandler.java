package net.xzh.kafka.listener;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * 消息结果回调类
 */
@SuppressWarnings("rawtypes")
@Component
public class KafkaSendResultHandler implements ProducerListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaSendResultHandler.class);


    @Override
	public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        log.info("发送成功 ,{}",producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord producerRecord, RecordMetadata recordMetadata, Exception exception) {
    	 log.info("发送失败 ,{}",producerRecord.toString());
    }
}
