package net.xzh.datasource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * 通过@MapperScan注解指定扫描的mapper接口所在的包路径
 * 
 * @author vjsp
 * @date 2025年10月05日
 */

@MapperScan(basePackages = "net.xzh.**.mapper")
@Configuration
public class MyBatisConfig {

}
