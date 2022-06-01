package net.xzh.gen.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by xuzhihao on 2019/4/8.
 */
@Configuration
@MapperScan({"net.xzh.gen.mapper","net.xzh.gen.dao"})
public class MyBatisConfig {
}
