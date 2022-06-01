package net.xzh.elasticsearch.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by macro on 2019/4/8.
 */
@Configuration
@MapperScan({"net.xzh.elasticsearch.mapper","net.xzh.elasticsearch.dao"})
public class MyBatisConfig {
}
