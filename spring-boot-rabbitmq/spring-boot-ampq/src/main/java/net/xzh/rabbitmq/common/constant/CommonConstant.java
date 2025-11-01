package net.xzh.rabbitmq.common.constant;

/**
 * 通用常量信息
 * @author xzh
 *
 */
public interface CommonConstant {
	
	//AMPQ============================================================
	/**
	 * 简单模式-队列名称
	 */
	String QUEUE_SIMPLE = "queue.simple";
	
	/**
	 * 工作模式-队列名称
	 */
	String QUEUE_WORK = "queue.work";
	
	/**
	 * 订阅模式-扇形交换机名称
	 */
	String EXCHANGE_FANOUT = "exchange.fanout";
	
	/**
	 * 路由模式-直接交换机名称
	 */
	String EXCHANGE_DIRECT = "exchange.direct";
	
	/**
	 * 主题模式-主题交换机名称
	 */
	String EXCHANGE_TOPIC = "exchange.topic";
}