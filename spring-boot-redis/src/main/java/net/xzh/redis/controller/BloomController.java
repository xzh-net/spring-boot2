package net.xzh.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.aspect.annotation.RedisBloom;
import net.xzh.redis.common.aspect.annotation.RedisBloomAdd;
import net.xzh.redis.common.model.CommonResult;

/**
 * 布隆过滤器
 * 
 * @author Administrator
 *
 */
@Api(tags = "布隆")
@RestController
@RequestMapping("/bloom")
public class BloomController {
	
	@Autowired
	private RedisTemplate<Object,Object> redisTemplate;// 来模拟数据库存
	
	@ApiOperation("设置产品")
    @RequestMapping(value = "/bloomSet", method = RequestMethod.GET)
	@RedisBloomAdd(key = "productBloom", value = "#id")
	public CommonResult<?> bloomSet(@RequestParam String id) {
		int stock = Convert.toInt(redisTemplate.opsForValue().get(id),0);
		if(stock>0) {
			redisTemplate.opsForValue().set(id, stock+1);
		}else {
			redisTemplate.opsForValue().set(id, 1);
		}
		return CommonResult.success(1);
	}

    @ApiOperation("穿透测试")
    @RequestMapping(value = "/bloomGet", method = RequestMethod.GET)
    @RedisBloom(key = "productBloom", value = "#id")
	public CommonResult<?> bloomGet(@RequestParam String id) {
		return CommonResult.success(redisTemplate.opsForValue().get(id));
	}
}