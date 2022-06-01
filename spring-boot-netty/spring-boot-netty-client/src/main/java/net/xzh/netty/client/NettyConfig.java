package net.xzh.netty.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfig {
	@Value("${channel.id}")
	private long id;

	@Bean
	public Protocol protocol() {
		return new Protocol(id, System.currentTimeMillis() + "");
	}
}