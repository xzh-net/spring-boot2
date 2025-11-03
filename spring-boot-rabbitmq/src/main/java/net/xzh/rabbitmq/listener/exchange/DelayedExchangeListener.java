package net.xzh.rabbitmq.listener.exchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import net.xzh.rabbitmq.config.exchange.DelayedExchangeConfig;

/**
 * 延迟消息交换机监听器
 * 
 * 相比死信队列实现延迟的优势： 1. 更精确的延迟控制 2. 支持消息级别的延迟时间 3. 无需创建多个队列实现不同延迟时间 4. 更好的性能表现
 */
@Component
public class DelayedExchangeListener {

	private static final Logger log = LoggerFactory.getLogger(DelayedExchangeListener.class);

	/**
	 * 监听延迟消息队列
	 * 
	 * 处理从延迟交换机路由过来的延迟到期消息 使用手动确认模式确保消息处理可靠性
	 * 
	 * @param message RabbitMQ消息对象
	 * @param headers 消息头信息
	 * @param channel RabbitMQ通道
	 * @throws IOException 通道操作异常
	 */
	@RabbitListener(queues = DelayedExchangeConfig.DELAYED_QUEUE)
	public void handleDelayedMessage(Message message, @Headers Map<String, Object> headers, Channel channel)
			throws IOException {
		long deliveryTag = message.getMessageProperties().getDeliveryTag();
		String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

		try {
			log.info("延迟队列接收到消息, deliveryTag: {}, message: {}", deliveryTag, messageBody);

			// 处理延迟消息
			processDelayedMessage(messageBody, headers);

			// 消息处理成功，手动确认
			channel.basicAck(deliveryTag, false);
			log.info("延迟消息处理完成并确认, deliveryTag: {}", deliveryTag);

		} catch (Exception e) {
			log.error("处理延迟消息时发生错误, deliveryTag: {}, message: {}", deliveryTag, messageBody, e);

			// 处理消息处理失败的情况
			handleDelayedMessageFailure(message, deliveryTag, channel, e);
		}
	}

	/**
	 * 处理延迟消息的核心业务逻辑
	 * 
	 * @param messageBody   消息内容
	 * @param headers       消息头信息
	 * @param originalDelay 设定的延迟时间
	 * @param actualDelay   实际的延迟时间
	 */
	private void processDelayedMessage(String messageBody, Map<String, Object> headers) {
		// 根据消息内容进行不同的业务处理
		// 示例：根据业务类型路由到不同的处理方法

		if (messageBody.contains("order_cancel")) {
			// 取消订单
		} else if (messageBody.contains("cache_refresh")) {
			// 订单刷新
		} else if (messageBody.contains("notification")) {
			// 订单通知
		} else if (messageBody.contains("retry")) {
			// 订单重试
		} else {
			processDefaultDelayed(messageBody);
		}
	}

	/**
	 * 处理默认延迟任务
	 */
	private void processDefaultDelayed(String messageBody) {
		log.info("处理默认延迟任务, 内容: {}", messageBody);

		// 默认的延迟任务处理逻辑
		// 记录日志、执行通用业务等

		// defaultDelayedService.process(messageBody);
	}

	/**
	 * 处理延迟消息处理失败的情况
	 * 
	 * @param message     原始消息
	 * @param deliveryTag 消息标签
	 * @param channel     RabbitMQ通道
	 * @param exception   异常信息
	 * @throws IOException 通道操作异常
	 */
	private void handleDelayedMessageFailure(Message message, long deliveryTag, Channel channel, Exception exception)
			throws IOException {
		// 获取重试次数
		Map<String, Object> headers = message.getMessageProperties().getHeaders();
		Integer retryCount = (Integer) headers.get("x-retry-count");
		if (retryCount == null) {
			retryCount = 0;
		}

		// 判断是否达到最大重试次数
		if (retryCount < 3) {
			// 未达到最大重试次数，重新入队
			log.warn("延迟消息处理失败，准备重试，当前重试次数: {}", retryCount);

			// 设置重试次数
			headers.put("x-retry-count", retryCount + 1);

			// 拒绝消息并重新入队
			channel.basicNack(deliveryTag, false, true);
		} else {
			// 达到最大重试次数，确认消息并记录错误
			log.error("延迟消息达到最大重试次数，进入最终失败处理，重试次数: {}", retryCount);

			// 确认消息，不再重试
			channel.basicAck(deliveryTag, false);

			// 记录最终失败的消息
			// failedDelayedMessageService.recordFinalFailure(message, exception);

			// 或者发送到专门的失败处理队列
			// channel.basicPublish("delayed.failure.exchange", "delayed.failure.key", null,
			// message.getBody());
		}
	}

	/**
	 * 延迟消息插件的优势场景： 1. 精准定时任务：需要精确时间触发的业务 2. 分级延迟：不同业务需要不同的延迟时间 3.
	 * 动态延迟：根据业务逻辑动态计算延迟时间 4. 大规模延迟任务：需要管理大量不同延迟时间的任务
	 * 
	 * 注意事项： 1. 延迟时间不宜过长（建议不超过数天） 2. 大量延迟消息可能占用较多内存 3.
	 * 重启RabbitMQ可能导致未到期的延迟消息丢失（取决于持久化配置）
	 */
}