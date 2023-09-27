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
public class WsApplication {
	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}
}
