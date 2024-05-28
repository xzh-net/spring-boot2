
package net.xzh.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 发布订阅模式
 */
@Service
public class FanoutExchangeListener {

    private static final Logger log = LoggerFactory.getLogger(FanoutExchangeListener.class);

    @RabbitListener(queues = "#{fanoutQueue1}")
    public void receive1(String message) {
    	log.info("{}订阅到了消息,topic={}", CommonConstant.EXCHANGE_FANOUT, message);
    }

    @RabbitListener(queues = "#{fanoutQueue2}")
    public void receive2(String message) {
    	log.info("{}订阅到了消息,topic={}", CommonConstant.EXCHANGE_FANOUT, message);
    }

}
