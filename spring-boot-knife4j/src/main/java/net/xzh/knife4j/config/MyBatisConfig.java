package net.xzh.knife4j.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by xuzhihao 2019/4/8.
 */
@Configuration
@MapperScan("net.xzh.knife4j.mapper")
public class MyBatisConfig {
}
