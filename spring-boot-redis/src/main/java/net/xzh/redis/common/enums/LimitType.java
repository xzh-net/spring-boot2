package net.xzh.redis.common.enums;

/**
 * 限流类型
 * 
 */
public enum LimitType {

	/**
	 * 根据请求IP进行限流
	 */
	IP,

	/**
	 * 用户
	 */
	USER,

	/**
	 * 默认策略全局限流（用户+URL+参数）
	 * 
	 */
	DEFAULT

}
