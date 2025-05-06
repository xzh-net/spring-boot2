package net.xzh.geode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;

@SpringBootApplication
@ClientCacheApplication
public class GeoDeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoDeApplication.class, args);
	}
}
