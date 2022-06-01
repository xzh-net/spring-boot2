package net.xzh.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.annotation.RedisBloom;
import net.xzh.redis.common.annotation.RedisBloomAdd;
import net.xzh.redis.common.model.CommonResult;

/**
 * 布隆过滤器
 * 
 * @author Administrator
 *
 */
@Api(tags = "布隆过滤器测试")
@RestController
@RequestMapping("/bloom")
public class BloomController {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;// 来模拟数据库存
	
	@ApiOperation("冲减库存+1")
    @RequestMapping(value = "/bloomSet", method = RequestMethod.GET)
	@RedisBloomAdd(key = "testBloomFilter", value = "#id")
	public CommonResult bloomSet(@RequestParam String id) {
		int stock = Convert.toInt(stringRedisTemplate.opsForValue().get(id),0);
		if(stock>0) {
			stringRedisTemplate.opsForValue().set(id, StrUtil.toString(stock+1));
		}else {
			stringRedisTemplate.opsForValue().set(id, "1");
		}
		return CommonResult.success(1);
	}

    @ApiOperation("穿透测试")
    @RequestMapping(value = "/bloomGet", method = RequestMethod.GET)
    @RedisBloom(key = "testBloomFilter", value = "#id")
	public CommonResult bloomGet(@RequestParam String id) {
		return CommonResult.success(stringRedisTemplate.opsForValue().get(id));
	}
}