package net.xzh.api.annotation;

import java.lang.annotation.*;

/**
 * 自定义版本注解
 * @author xzh
 * @date 2024/9/18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 标识版本号，从1开始
     */
    int value() default 1;

}
