package net.xzh.redis.common.model;

/**
 * @author Redis 限流类型
 * @date 2020/4/8 13:47
 */
public enum LimitType {

	/**
	 * 自定义key
	 */
	CUSTOMER,

	/**
	 * 请求ip
	 */
	IP,

	/**
	 * userid+url md5
	 */
	DEFAULT;
}