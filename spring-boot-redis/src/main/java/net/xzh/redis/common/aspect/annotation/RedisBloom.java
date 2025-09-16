package net.xzh.redis.common.aspect.annotation;

import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 布隆过滤器自定义注解
 * @author CR7
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(1)
public @interface RedisBloom {
    /**
     *  使用布隆过滤器 的key
     */
    String key() default "";

    /**
     *  传入的入参
     */
    String value() default "";


}
