package net.xzh.log.common.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 审计日志配置
 *
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "audit-log")
public class AuditLogProperties {
    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;
    /**
     * 日志记录类型(log，db)
     */
    private String logType;
}
