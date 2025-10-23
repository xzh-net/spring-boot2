package net.xzh.sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
public class SftpApplication {
	public static void main(String[] args) {
		SpringApplication.run(SftpApplication.class, args);
	}
}
