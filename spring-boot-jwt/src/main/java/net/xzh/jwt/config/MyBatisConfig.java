package net.xzh.jwt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created 2019/4/8.
 */
@Configuration
@MapperScan({"net.xzh.jwt.mapper","net.xzh.jwt.dao"})
public class MyBatisConfig {
}
