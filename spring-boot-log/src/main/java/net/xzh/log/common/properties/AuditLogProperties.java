package net.xzh.log.common.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 审计日志配置
 *
 * @author zlt
 * @date 2020/2/3
 * <p>
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "xzh.audit-log")
public class AuditLogProperties {
    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;
    /**
     * 日志记录类型(logger/redis/db/es)
     */
    private String logType;
}
