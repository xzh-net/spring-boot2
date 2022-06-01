package net.xzh.redis.config;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: redisson配置
 * @Description:
 */
@Configuration
public class RedissonConfig {

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() throws IOException {
		Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson.yml"));
		return Redisson.create(config);
	}
}


