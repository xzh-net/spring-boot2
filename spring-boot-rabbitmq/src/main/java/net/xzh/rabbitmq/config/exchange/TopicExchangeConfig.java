package net.xzh.rabbitmq.config.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.AMPQConstant;

/**
 * 主题模式 - 主题交换机配置
 */
@Configuration
public class TopicExchangeConfig {

	// 队列名称常量
	public static final String TOPIC_QUEUE_1 = "topic.queue1";
	public static final String TOPIC_QUEUE_2 = "topic.queue2";

	// 路由键模式常量
	public static final String ROUTING_PATTERN_ORANGE = "*.orange.*";
	public static final String ROUTING_PATTERN_RABBIT = "*.*.rabbit";
	public static final String ROUTING_PATTERN_LAZY = "lazy.#";

	/**
	 * 创建主题交换机 主题交换机根据路由键的模式匹配进行消息路由 支持通配符：* 匹配一个单词，# 匹配零个或多个单词
	 */
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(AMPQConstant.EXCHANGE_TOPIC);
	}

	/**
	 * 主题队列1 - 用于接收橙色相关和兔子相关的消息 路由模式： - *.orange.* : 匹配中间为orange的三段路由键 - *.*.rabbit
	 * : 匹配最后为rabbit的三段路由键
	 */
	@Bean
	public Queue topicQueue1() {
		// 参数说明：队列名称, 是否持久化, 是否独占, 是否自动删除
		return new Queue(TOPIC_QUEUE_1, true, false, false);
	}

	/**
	 * 主题队列2 - 用于接收懒惰相关的消息 路由模式： - lazy.# : 匹配以lazy开头的任意长度路由键
	 */
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE_2, true, false, false);
	}

	/**
	 * 绑定队列1到主题交换机 - 橙色模式 示例匹配：quick.orange.rabbit, lazy.orange.elephant
	 */
	@Bean
	public Binding topicBindingOrange(TopicExchange topicExchange, Queue topicQueue1) {
		return BindingBuilder.bind(topicQueue1).to(topicExchange).with(ROUTING_PATTERN_ORANGE);
	}

	/**
	 * 绑定队列1到主题交换机 - 兔子模式 示例匹配：quick.orange.rabbit, lazy.pink.rabbit
	 */
	@Bean
	public Binding topicBindingRabbit(TopicExchange topicExchange, Queue topicQueue1) {
		return BindingBuilder.bind(topicQueue1).to(topicExchange).with(ROUTING_PATTERN_RABBIT);
	}

	/**
	 * 绑定队列2到主题交换机 - 懒惰模式 示例匹配：lazy.orange, lazy.orange.rabbit, lazy.pink.rabbit
	 */
	@Bean
	public Binding topicBindingLazy(TopicExchange topicExchange, Queue topicQueue2) {
		return BindingBuilder.bind(topicQueue2).to(topicExchange).with(ROUTING_PATTERN_LAZY);
	}
}