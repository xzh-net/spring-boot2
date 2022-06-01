package net.xzh.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.annotation.Limit;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.common.model.LimitType;

/**
 * 限流测试
 * 
 * @author Administrator
 *
 */
@Api(tags = "限流测试")
@RestController
@RequestMapping("/limit")
public class LimitController {

	@Limit
	@ApiOperation("默认限制")
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public CommonResult defaultlimit(@RequestParam String id) {
		return CommonResult.success(1);
	}

	@Limit(limitType = LimitType.IP)
	@ApiOperation("ip限制")
	@RequestMapping(value = "/ip", method = RequestMethod.GET)
	public CommonResult ip(@RequestParam String id) {
		return CommonResult.success(1);
	}

	@Limit(key = "customer_limit_test", limitType = LimitType.CUSTOMER)
	@ApiOperation("自定义限制")
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public CommonResult customer(@RequestParam String id) {
		return CommonResult.success(1);
	}

}