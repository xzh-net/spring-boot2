package net.xzh.log.aspect.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.xzh.log.model.BusinessType;

/**
 * 审计日志注解定义
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作信息
     */
    String operation();
    
    /**
     * 业务类型
     */
    BusinessType businessType() default BusinessType.OTHER;
}
