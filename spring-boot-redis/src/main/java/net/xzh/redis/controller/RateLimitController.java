package net.xzh.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.redis.common.aspect.annotation.RateLimiter;
import net.xzh.redis.common.enums.LimitType;
import net.xzh.redis.common.model.CommonResult;

/**
 * 三种限流类型测试
 * 
 */
@Tag(name = "测试限流", description = "测试限流")
@RestController
@RequestMapping("/limit")
public class RateLimitController {

	@Operation(summary = "默认规则", description = "md5(身份+uri+参数)做为key")
	@RateLimiter(max = 10, timeout = 60)
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public CommonResult<?> defaultlimit(@RequestParam String id) {
		return CommonResult.success(1);
	}

	@Operation(summary = "按IP", description = "以IP为key")
	@RateLimiter(limitType = LimitType.IP, max = 10, timeout = 60)
	@RequestMapping(value = "/ip", method = RequestMethod.GET)
	public CommonResult<?> ip(@RequestParam String id) {
		return CommonResult.success(1);
	}

	
	@Operation(summary = "自定义key", description = "直接以参数做为key")
	@RateLimiter(key = "customer_limit_test", limitType = LimitType.USER, max = 10, timeout = 60)
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public CommonResult<?> customer(@RequestParam String id) {
		return CommonResult.success(1);
	}

}