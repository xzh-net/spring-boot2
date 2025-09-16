package net.xzh.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.aspect.annotation.RateLimiter;
import net.xzh.redis.common.enums.LimitType;
import net.xzh.redis.common.model.CommonResult;

/**
 * 三种限流类型测试
 * 
 */
@Api(tags = "限流")
@RestController
@RequestMapping("/limit")
public class RateLimitController {

	@RateLimiter(max = 10, timeout = 60)
	@ApiOperation("默认")
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public CommonResult<?> defaultlimit(@RequestParam String id) {
		return CommonResult.success(1);
	}

	@RateLimiter(limitType = LimitType.IP, max = 10, timeout = 60)
	@ApiOperation("按ip")
	@RequestMapping(value = "/ip", method = RequestMethod.GET)
	public CommonResult<?> ip(@RequestParam String id) {
		return CommonResult.success(1);
	}

	@RateLimiter(key = "customer_limit_test", limitType = LimitType.USER, max = 10, timeout = 60)
	@ApiOperation("按用户自定义")
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public CommonResult<?> customer(@RequestParam String id) {
		return CommonResult.success(1);
	}

}