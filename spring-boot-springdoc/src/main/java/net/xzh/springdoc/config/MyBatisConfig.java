package net.xzh.springdoc.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 */
@Configuration
@MapperScan("net.xzh.springdoc.mapper")
public class MyBatisConfig {
}
