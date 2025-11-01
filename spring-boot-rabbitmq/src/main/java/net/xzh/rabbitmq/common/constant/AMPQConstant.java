package net.xzh.rabbitmq.common.constant;

/**
 * 通用常量信息
 * @author xzh
 *
 */
public interface AMPQConstant {
	
	//AMPQ 队列名称============================================================
	/**
	 * 简单模式-队列名称
	 */
	String QUEUE_SIMPLE = "queue.simple";
	
	/**
	 * 工作模式-队列名称
	 */
	String QUEUE_WORK = "queue.work";
	
	
	//AMPQ 交换机E名称============================================================
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
	
	/**
	 * 死信发送交换机名称
	 */
	String EXCHANGE_DLX = "exchange.dlx";
	
	/**
	 * 死信接收交换机名称
	 */
	String EXCHANGE_DLX_RECEIVE =  "exchange.dlx.user.order";
	
	/**
	 * 延迟交换机名称
	 */
	String EXCHANGE_DELAY =  "exchange.delay";
	
	/**
	 * 有确认回执的交换机名称
	 */
	String EXCHANGE_ACK =  "exchange.ack";
	
}