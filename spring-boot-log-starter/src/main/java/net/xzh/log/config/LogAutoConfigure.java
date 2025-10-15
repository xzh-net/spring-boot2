package net.xzh.log.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;

import net.xzh.log.properties.AuditLogProperties;
import net.xzh.log.properties.LogDbProperties;

/**
 * 日志自动配置
 *
 * @author zlt
 * @date 2019/8/13
 */
@ComponentScan
@EnableConfigurationProperties({AuditLogProperties.class})
public class LogAutoConfigure {
    /**
     * 日志数据库配置
     */
    @Configuration
    @ConditionalOnClass(HikariConfig.class)
    @EnableConfigurationProperties(LogDbProperties.class)
    public static class LogDbAutoConfigure {}
}
