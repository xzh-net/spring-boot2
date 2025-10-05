package net.xzh.redis.common.aspect;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.crypto.SecureUtil;
import net.xzh.redis.common.aspect.annotation.RateLimiter;
import net.xzh.redis.common.enums.LimitType;
import net.xzh.redis.common.exception.ApiException;
import net.xzh.redis.common.utils.IpUtil;

/**
 * 限流切面实现
 * 
 * @author xzh
 *
 */
@Aspect
@Component
public class RateLimiterAspect {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisScript<Long> limitScript;

	@Before("@annotation(rateLimiter)")
	public void doBefore(JoinPoint joinPoint, RateLimiter rateLimiter) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Object[] args = joinPoint.getArgs();
		String key;
		// 当前时间戳
		long now = Instant.now().toEpochMilli();
		TimeUnit timeUnit = rateLimiter.timeUnit();
		// 时间窗口内的最大限额
		long max = rateLimiter.max();
		long timeout = rateLimiter.timeout();
		// 一个时间窗口的毫秒数，用于设置Key的过期时间
		long ttl = timeUnit.toMillis(timeout);
		// 过期时间点的时间戳（now - 时间窗口大小）
		long expired = now - ttl;
		LimitType limitType = rateLimiter.limitType();
		String token = request.getHeader("Authorization");
		switch (limitType) {
		case IP:
			key = IpUtil.getIpAddr();
			break;
		case USER:
			key = rateLimiter.key();
			break;
		default:
			key = SecureUtil.md5(token + "-" + request.getRequestURI() + "-" + Arrays.asList(args));
			break;
		}
		// 限流的资源key
		List<String> keys = Collections.singletonList(rateLimiter.prefix() + key);
	    Long number = stringRedisTemplate.execute(limitScript, 
	        keys, 
	        String.valueOf(now), 
	        String.valueOf(ttl), 
	        String.valueOf(expired), 
	        String.valueOf(max));
	        
	    if (number == null || number == 0) {
	        throw new ApiException("访问频繁，请稍候再试");
	    }
	}

}
