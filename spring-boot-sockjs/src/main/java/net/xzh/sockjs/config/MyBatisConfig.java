package net.xzh.sockjs.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis相关配置
 */
@Configuration
@MapperScan({"net.xzh.sockjs.mapper"})
public class MyBatisConfig {
}
