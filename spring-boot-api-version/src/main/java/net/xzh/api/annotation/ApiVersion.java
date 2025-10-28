package net.xzh.api.annotation;

import java.lang.annotation.*;

/**
 * 定义版本控制注解，可标注在类和方法上
 * 运行时生效，支持从1开始的版本号
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
