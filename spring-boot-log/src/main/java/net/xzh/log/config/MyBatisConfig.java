package net.xzh.log.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("net.xzh.log.mapper")
public class MyBatisConfig {
}
