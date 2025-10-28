package net.xzh.cxf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.xzh.cxf.core.annotation.EnableWs;

/**
 * cxf
 * @author Administrator
 *
 */
@EnableWs
@SpringBootApplication
public class WebServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}
}
