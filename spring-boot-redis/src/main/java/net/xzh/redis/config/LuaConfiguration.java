package net.xzh.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * 获取lua脚本
 * 
 * @author Administrator
 *
 */

@Configuration
public class LuaConfiguration {
	@Bean
	public DefaultRedisScript<Long> limitRedisScript() {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/scripts/redis/limit.lua")));
		redisScript.setResultType(Long.class);
		return redisScript;
	}
}