package net.xzh.oss.modules.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created 2019/4/8.
 */
@Configuration
@MapperScan("net.xzh.oss.modules.mapper")
public class MyBatisConfig {
}
