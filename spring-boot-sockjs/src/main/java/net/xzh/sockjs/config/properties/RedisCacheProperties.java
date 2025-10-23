package net.xzh.sockjs.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * redis缓存注解属性
 *
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "redis.cache")
@Component
public class RedisCacheProperties {
    private List<CacheConfig> configs;

    @Setter
    @Getter
    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，单位：默认3600秒
         */
        private long second = 3600;
    }
}
