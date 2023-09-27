package net.xzh.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder;

import io.lettuce.core.ReadFrom;

@SpringBootApplication
@EnableCaching
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public LettuceClientConfigurationBuilderCustomizer clientConfigurationBuilderCustomizer() {
		return new LettuceClientConfigurationBuilderCustomizer() {
			@Override
			public void customize(LettuceClientConfigurationBuilder clientConfigurationBuilder) {
				clientConfigurationBuilder.readFrom(ReadFrom.REPLICA_PREFERRED);
			}
		};
	}
}