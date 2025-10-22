package net.xzh.mq.listener;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.xzh.mq.domain.Order;

/**
 * 订单消息客户端
 * 
 * @author xzh
 *
 */
@Service
@RocketMQMessageListener(consumerGroup = "order-topic", // 消费者组名
		topic = "order-topic", // 消费主题
		consumeMode = ConsumeMode.ORDERLY, // 消费模式,指定是否顺序消费 CONCURRENTLY(同步,默认) ORDERLY(顺序)
		messageModel = MessageModel.CLUSTERING// 消息模式 BROADCASTING(广播) CLUSTERING(集群,默认)
)
public class OrderMessageListenerImpl implements RocketMQListener<MessageExt> {// 需要记录消息信息使用MessageExt，否则直接使用Order接收数据

	private static final Logger log = LoggerFactory.getLogger(OrderMessageListenerImpl.class);

	// 消费逻辑
	@Override
	public void onMessage(MessageExt message) {
		try {
			// 1. 解析消息内容
			String body = new String(message.getBody(), "UTF-8");
			Order order = JSON.parseObject(body, Order.class);
			log.info("{},接收到订单{}", message.getMsgId(), order);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	class Param {
		private String code;
	}
}
