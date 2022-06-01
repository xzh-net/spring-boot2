package net.xzh.swagger.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created xzh 2019/4/8.
 */
@Configuration
@MapperScan("net.xzh.swagger.mapper")
public class MyBatisConfig {
}
