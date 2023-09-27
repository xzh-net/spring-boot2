package net.xzh.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * 流程设计器 
 * @author CR7
 *
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ActivitiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ActivitiApplication.class, args);
	}
}