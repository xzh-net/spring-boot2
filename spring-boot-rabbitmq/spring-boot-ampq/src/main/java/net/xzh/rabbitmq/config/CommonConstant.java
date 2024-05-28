package net.xzh.rabbitmq.config;

/**
 * 全局公共常量
 * @author CR7
 *
 */
public interface CommonConstant {
	/**
	 * 简单模式队列名称
	 */
	String QUEUE_SIMPLE = "queue.simple";
	
	/**
	 * 工作模式队列名称
	 */
	String QUEUE_WORK = "queue.work";
	
	/**
	 * 订阅模式Fanout交换机名称
	 */
	String EXCHANGE_FANOUT = "exchange.fanout";
	
	/**
	 * 路由模式交换机名称
	 */
	String EXCHANGE_DIRECT = "exchange.direct";
	
	/**
	 * 主题模式交换机名称
	 */
	String EXCHANGE_TOPIC = "exchange.topic";
}