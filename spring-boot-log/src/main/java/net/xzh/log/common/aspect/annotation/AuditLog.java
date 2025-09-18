package net.xzh.log.common.aspect.annotation;


import java.lang.annotation.*;

import net.xzh.log.common.enums.BusinessType;

/**
 * @author zlt
 * @date 2020/2/3
 * <p>
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
