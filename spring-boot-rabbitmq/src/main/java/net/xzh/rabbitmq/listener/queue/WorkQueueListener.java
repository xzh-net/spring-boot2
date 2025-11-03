package net.xzh.rabbitmq.listener.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 工作队列模式监听器 工作队列模式：多个消费者竞争消费，实现负载均衡
 */
@Service
public class WorkQueueListener {

	private static final Logger log = LoggerFactory.getLogger(WorkQueueListener.class);

	// 消费者标识，用于区分多个消费者实例
	private final String consumerId;

	public WorkQueueListener() {
		// 生成消费者标识（在实际应用中可以使用应用实例ID）
		this.consumerId = "WORKER-" + System.currentTimeMillis() % 1000;
	}

	/**
	 * 监听工作队列 - 多个消费者实例将竞争消费消息 消息将在多个消费者之间轮询分发（Round-robin）
	 * 
	 * 配置说明： 为了实现公平分发而不是轮询分发，需要在配置中设置prefetch=1 这样每个消费者一次只获取一个消息，处理完成后再获取下一个
	 * 
	 * @param message 接收到的消息内容
	 */
	@RabbitListener(queues = AMPQConstant.QUEUE_WORK)
	public void handleWorkMessage(String message) {
		long startTime = System.currentTimeMillis();

		try {
			log.info("工作队列[消费者{}]接收到消息, 开始处理: message={}", consumerId, message);

			// 模拟任务处理 - 根据消息内容模拟不同的处理时间
			processWorkTask(message);

			long processingTime = System.currentTimeMillis() - startTime;
			log.info("工作队列[消费者{}]消息处理完成, 耗时: {}ms, message={}", consumerId, processingTime, message);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("工作队列[消费者{}]消息处理被中断, message: {}", consumerId, message);

		} catch (Exception e) {
			long failureTime = System.currentTimeMillis() - startTime;
			log.error("工作队列[消费者{}]消息处理失败, 耗时: {}ms, message: {}", consumerId, failureTime, message, e);

			// 工作队列的异常处理策略：
			handleWorkQueueException(message, e);
		}
	}

	/**
	 * 处理工作任务的业务逻辑 模拟不同的处理时间，展示工作队列的负载均衡特性
	 * 
	 * @param message 消息内容
	 * @throws InterruptedException 处理被中断
	 */
	private void processWorkTask(String message) throws InterruptedException {
		// 根据消息内容模拟不同的处理复杂度
		long processingTime;

		if (message.contains("urgent")) {
			// 紧急任务 - 快速处理
			processingTime = 1000;
			log.debug("处理紧急任务: {}", message);
		} else if (message.contains("complex")) {
			// 复杂任务 - 耗时处理
			processingTime = 5000;
			log.debug("处理复杂任务: {}", message);
		} else {
			// 普通任务 - 中等处理时间
			processingTime = 2000;
			log.debug("处理普通任务: {}", message);
		}

		// 模拟任务处理时间
		Thread.sleep(processingTime);

		// 这里可以添加实际的业务逻辑
		// yourBusinessService.processWorkItem(message);
	}

	/**
	 * 工作队列异常处理策略
	 * 
	 * @param message   消息内容
	 * @param exception 异常信息
	 */
	private void handleWorkQueueException(String message, Exception exception) {
		// 根据异常类型和业务需求决定处理策略

		if (exception instanceof IllegalArgumentException) {
			// 参数错误 - 消息格式问题，记录日志但不再重试
			log.warn("工作队列消息格式错误，跳过处理: {}", message);
			// 可以发送到死信队列进行分析

		} else if (exception instanceof RuntimeException) {
			// 业务逻辑异常 - 可能需要重试
			log.warn("工作队列业务处理异常，可能需要重试: {}", message);
			// 抛出异常让消息重新入队（需要配置重试机制）
			throw new RuntimeException("工作队列处理失败，需要重试", exception);

		} else {
			// 其他异常 - 系统级错误
			log.error("工作队列系统级异常: {}", message);
			// 根据业务需求决定是否重试或进入死信队列
		}

		// 在实际应用中，可以结合重试机制和死信队列
		// 例如：重试3次后进入死信队列进行人工处理
	}

	/**
	 * 如果需要获取更多消息属性，可以使用Message对象
	 * 
	 * @param message
	 */
//	@RabbitListener(queues = AMPQConstant.QUEUE_WORK)
	public void handleWorkMessageWithDetails(Message message) {
		String body = new String(message.getBody());
		int priority = message.getMessageProperties().getPriority();

		log.info("工作队列消息详情 - 优先级: {}, 内容: {}", priority, body);
	}

}