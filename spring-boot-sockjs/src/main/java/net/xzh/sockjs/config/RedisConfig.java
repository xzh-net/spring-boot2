package net.xzh.sockjs.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import net.xzh.sockjs.config.properties.RedisCacheProperties;

/**
 * 缓存配置
 * 开启@EnableCaching注解
 */

@Configuration
@EnableCaching
public class RedisConfig {
	@Autowired
	private RedisCacheProperties redisCacheProperties;

	/**
	 * 注入key的序列化
	 * 
	 * @return
	 */
	@Bean
	public RedisSerializer<String> stringKeySerializer() {
		return new StringRedisSerializer();
	}

	/**
	 * 注入json序列化
	 * 
	 * @return
	 */
	@Bean
	public RedisSerializer<Object> jsonValueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}


	/**
	 * 推荐注入RedisTemplate<String, Object>，代码可读性好
	 * 
	 * @param factory
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory,
			RedisSerializer<String> stringKeySerializer, RedisSerializer<Object> jsonValueSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

	    // 使用String序列化key
		redisTemplate.setKeySerializer(stringKeySerializer);
		redisTemplate.setHashKeySerializer(stringKeySerializer);
		
		// 使用JSON序列化value
		redisTemplate.setValueSerializer(jsonValueSerializer);
		redisTemplate.setHashValueSerializer(jsonValueSerializer);
		
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	@Bean(name = "cacheManager")
	@Primary
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
			RedisSerializer<String> stringKeySerializer, RedisSerializer<Object> jsonValueSerializer) {
		RedisCacheConfiguration difConf = getDefConf(stringKeySerializer, jsonValueSerializer)
				.entryTtl(Duration.ofHours(1));
		// 自定义的缓存过期时间配置
		int configSize = redisCacheProperties.getConfigs() == null ? 0 : redisCacheProperties.getConfigs().size();
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(configSize);
		if (configSize > 0) {
			redisCacheProperties.getConfigs().forEach(e -> {
				RedisCacheConfiguration conf = getDefConf(stringKeySerializer, jsonValueSerializer)
						.entryTtl(Duration.ofSeconds(e.getSecond()));
				redisCacheConfigurationMap.put(e.getKey(), conf);
			});
		}

		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(difConf)
				.withInitialCacheConfigurations(redisCacheConfigurationMap).build();
	}
	
	
	private RedisCacheConfiguration getDefConf(RedisSerializer<String> stringKeySerializer,
			RedisSerializer<Object> jsonValueSerializer) {
		return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
				.computePrefixWith(cacheName -> "annotation".concat(":").concat(cacheName).concat(":"))
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringKeySerializer))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonValueSerializer));
	}

	@Bean
	public DefaultRedisScript<Long> limitScript() {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(limitScriptText());
		redisScript.setResultType(Long.class);
		return redisScript;
	}

	/**
	 * 滑动时间窗口算法限流脚本
	 */
	private String limitScriptText() {
		return "local key = KEYS[1]\n" + "local now = tonumber(ARGV[1])\n" + "local ttl = tonumber(ARGV[2])\n"
				+ "local expired = tonumber(ARGV[3])\n" + "local max = tonumber(ARGV[4])\n"
				+ "redis.call('zremrangebyscore', key, 0, expired)\n"
				+ "local current = tonumber(redis.call('zcard', key))\n" + "local next = current + 1\n"
				+ "if next > max then\n" + "  return 0;\n" + "else\n" + "  redis.call(\"zadd\", key, now, now)\n"
				+ "  redis.call(\"pexpire\", key, ttl)\n" + "  return next\n" + "end";
	}
	
}
