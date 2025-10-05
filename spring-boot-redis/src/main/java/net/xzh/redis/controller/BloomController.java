package net.xzh.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.convert.Convert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.redis.common.aspect.annotation.RedisBloom;
import net.xzh.redis.common.aspect.annotation.RedisBloomAdd;
import net.xzh.redis.common.model.CommonResult;

/**
 * 布隆过滤器
 * 
 * @author Administrator
 *
 */
@Tag(name = "布隆过滤器", description = "布隆过滤器测试")
@RestController
@RequestMapping("/bloom")
public class BloomController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;// 来模拟数据库存

	@Operation(summary = "设置产品库存", description = "有库存+1，没有则初始化")
	@RequestMapping(value = "/bloomSet", method = RequestMethod.GET)
	@RedisBloomAdd(key = "productBloom", value = "#id")
	public CommonResult<?> bloomSet(@RequestParam String id) {
		int stock = Convert.toInt(redisTemplate.opsForValue().get(id), 0);
		if (stock > 0) {
			redisTemplate.opsForValue().set(id, stock + 1);
		} else {
			redisTemplate.opsForValue().set(id, 1);
		}
		return CommonResult.success(1);
	}

	@Operation(summary = "查询产品库存", description = "按id访问产品，未命中返回500")
	@RequestMapping(value = "/bloomGet", method = RequestMethod.GET)
	@RedisBloom(key = "productBloom", value = "#id")
	public CommonResult<?> bloomGet(@RequestParam String id) {
		return CommonResult.success(redisTemplate.opsForValue().get(id));
	}
}