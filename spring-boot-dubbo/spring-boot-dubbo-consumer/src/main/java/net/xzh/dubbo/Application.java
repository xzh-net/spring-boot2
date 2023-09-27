package net.xzh.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 
 * @author CR7
 *
 */
@EnableDubbo
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除db自动配置
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}