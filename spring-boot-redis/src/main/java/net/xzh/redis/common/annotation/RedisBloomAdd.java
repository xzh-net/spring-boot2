package net.xzh.redis.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.Order;

/**
 * 布隆过滤器自定义注解添加值
 * @author CR7
 *
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(1)
public @interface RedisBloomAdd {
    /**
     * 使用布隆过滤器 的key
     */
    String key() default "";

    /**
     * 传入的入参
     */
    String value() default "";

}
