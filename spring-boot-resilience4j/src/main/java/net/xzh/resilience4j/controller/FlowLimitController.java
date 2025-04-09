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
	 * 调用失败重试5次，每次等待1s
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
	 * 熔断器circuitBreakerApi
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
	 * 限流
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
	 * 信号量控制
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
