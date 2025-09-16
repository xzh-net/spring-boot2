package net.xzh.redis.common.constant;

/**
 * 通用常量信息
 * 
 */
public class Constants {
	
	/*==============================全局常量=================================*/
	
	/**
	 * UTF-8 字符集
	 */
	public static final String UTF8 = "UTF-8";

	
	/*==============================Redis 缓存常量=================================*/
	
	/**
	 * 品牌【注解】
	 */
	public static final String CACHE_MALL_BRAND = "mall:brand";
	
	
	/**
	 * 产品
	 */
	public static final String CACHE_MALL_PRUDUCT = "mall:pruduct:";
	
	/**
	 * 短信登录
	 */
	public static final String CACHE_PHONE_CODE = "auth:phone:";
	
	/**
	 * 短信验证码长度
	 */
	
	public static final int CACHE_PHONE_CODE_LENGTH = 8;
	
	/**
	 * 秒杀分布式锁
	 */
	public static final String LOCK_PREFIX = "secKill:lock:";
	
	
}
