package net.xzh.redis.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import net.xzh.redis.common.model.LimitType;

/**
 * @author Limit (Redis实现)
 * @description 自定义限流注解
 * @date 2020/4/8 13:15
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {

	/**
	 * key
	 */
	String key() default "";

	/**
	 * 前缀
	 */
	String prefix() default "limit:";

	/**
	 * max 最大请求数
	 */
	long max() default 10;

	/**
	 * 超时时长，默认1分钟
	 */
	long timeout() default 1;

	/**
	 * 超时时间单位，默认 分钟
	 */
	TimeUnit timeUnit() default TimeUnit.MINUTES; //

	/**
	 * 限流的类型(用户自定义key 或者 请求ip)
	 */
	LimitType limitType() default LimitType.DEFAULT;
}
