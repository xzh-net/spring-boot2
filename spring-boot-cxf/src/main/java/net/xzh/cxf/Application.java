package net.xzh.cxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.xzh.cxf.common.annotation.EnableWs;

/**
 * cxf
 * @author Administrator
 *
 */
@EnableWs
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
