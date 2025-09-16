
package net.xzh.redis.common.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import net.xzh.redis.common.enums.LimitType;


/**
 * 限流注解
 * 
 * @author xzh
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

	/**
	 * key
	 */
	String key() default "";

	/**
	 * 前缀
	 */

	String prefix() default "rate_limit:";

	/**
	 * 限流次数
	 */
	long max() default 10;

	/**
	 * 限流时间，单位：秒
	 */
	long timeout() default 1;

	/**
	 * 限流时间单位，默认:秒
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS; //

	/**
	 * 限流的类型
	 */
	LimitType limitType() default LimitType.DEFAULT;
}
