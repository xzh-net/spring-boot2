package net.xzh.log.common.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志链路追踪配置
 *
 * @author zlt
 * @date 2019/8/13
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "xzh.trace")
public class TraceProperties {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = false;
}
