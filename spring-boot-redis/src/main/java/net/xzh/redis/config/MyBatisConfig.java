package net.xzh.redis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 持久层配置
 */
@Configuration
@MapperScan("net.xzh.redis.mapper")
public class MyBatisConfig {
}
