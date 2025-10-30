package net.xzh.resilience4j.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
@Slf4j
public class FlowLimitController {

	/**
	 * 重试：调用失败最多5次，每次间隔1秒
	 * 
	 * @return
	 */
	@GetMapping("/retry")
	@Retry(name = "retryApi", fallbackMethod = "fallback")
	public ResponseEntity<String> retryApi() {
		log.info("{}", "retryApi call received");
		new RestTemplate().getForObject("http://localhost:9999/", String.class);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	/**
	 * 熔断器：当调用量达到5次且失败率超过20%时，断路器会进入开路状态并持续5秒，之后进入半开状态允许试探性调用3次来检测服务是否恢复
	 * 
	 * @return
	 */
	@GetMapping("/circuitbreaker")
	@CircuitBreaker(name = "circuitBreakerApi", fallbackMethod = "fallback")
	public ResponseEntity<String> circuitBreakerApi() {
		log.info("{}", "circuitBreakerApi call received");
		int r = new Random().nextInt(100);
		if (r >= 70) {
			throw new RuntimeException("Unexcepted Exception");
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	/**
	 * 限流：1秒内最多只能调用1次
	 * 
	 * @return
	 */
	@GetMapping("/flowlimit")
	@RateLimiter(name = "flowlimitApi", fallbackMethod = "fallback")
	public ResponseEntity<String> flowlimitApi() {
		log.info("{}", "flowlimitApi call received");
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	/**
	 * 信号量：最大并发数量10
	 * 
	 * @return
	 */
	@GetMapping("/bulkhead")
	@Bulkhead(name = "backendA")
	public ResponseEntity<String> bulkhead() {
		return new ResponseEntity<>("bulkhead", HttpStatus.OK);
	}

	/**
	 * 通用处理
	 * 
	 * @param throwable
	 * @return
	 */
	public ResponseEntity<?> fallback(Throwable throwable) {
		log.error("fallback call received", throwable);
		return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
