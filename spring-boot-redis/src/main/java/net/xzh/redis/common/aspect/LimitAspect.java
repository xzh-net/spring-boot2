package net.xzh.redis.common.aspect;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import net.xzh.redis.common.annotation.Limit;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.common.model.LimitType;
import net.xzh.redis.common.utils.AddrUtil;

/**
 * 限流切面实现
 * 
 * @author Administrator
 *
 */
@Aspect
@Component
@Slf4j
public class LimitAspect {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private DefaultRedisScript<Long> redisScript;

	@Around("@annotation(limit)")
	public Object around(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Object[] args = joinPoint.getArgs();
		String key;
		long now = Instant.now().toEpochMilli();
		TimeUnit timeUnit = limit.timeUnit();
		long max = limit.max();
		long timeout = limit.timeout();
		long ttl = timeUnit.toMillis(timeout);
		long expired = now - ttl;
		LimitType limitType = limit.limitType();
		String jwtToken = request.getHeader("Authorization");
		switch (limitType) {
		case IP:
			key = AddrUtil.getRemoteAddr(request);
			break;
		case CUSTOMER:
			key = limit.key();
			break;
		default:
			key = SecureUtil.md5(jwtToken + "-" + request.getRequestURI() + "-" + Arrays.asList(args));
			break;
		}
		Long executeTimes = stringRedisTemplate.execute(redisScript, Collections.singletonList(limit.prefix() + key),
				now + "", ttl + "", expired + "", max + "");
		if (executeTimes != null) {
			if (executeTimes == 0) {
				log.error("【{}】在单位时间 {} 毫秒内已达到访问上限，当前接口上限 {}", key, ttl, max);
				return CommonResult.failed();
			} else {
				log.info("【{}】在单位时间 {} 毫秒内访问 {} 次", key, ttl, executeTimes);

			}
		}
		return joinPoint.proceed();
	}

}