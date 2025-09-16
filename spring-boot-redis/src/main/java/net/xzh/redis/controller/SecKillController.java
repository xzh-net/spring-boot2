package net.xzh.redis.controller;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.constant.Constants;
import net.xzh.redis.common.model.CommonResult;

/**
 * 分布式锁 实现方式：RedissonClient
 * 
 * @author Administrator
 *
 */
@Api(tags = "秒杀")
@RestController
@RequestMapping("/product")
public class SecKillController {

	@Autowired
	private RedisTemplate<Object,Object> redisTemplate;// 来模拟数据库存
	
	@Autowired
	private RedissonClient redissonClient;

	@ApiOperation("有锁下单")
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public CommonResult<?> payment(@RequestParam String id) {
		String rtn="库存不足";
		String lockKey=Constants.LOCK_PREFIX+id;
		String productKey=Constants.CACHE_MALL_PRUDUCT+id;
	    RLock lock = redissonClient.getLock(lockKey);
	    try {
	        // 尝试加锁，最多等待30秒，直到显示解锁
	        boolean isLocked = lock.tryLock(30, -1, TimeUnit.SECONDS);
	        if (isLocked) {
	            // 成功获取锁，执行你的业务代码
	        	int stock = Convert.toInt(redisTemplate.opsForValue().get(productKey),0);
				if (stock > 0) {
					int realStock = stock - 1;
					redisTemplate.opsForValue().set(productKey, realStock);
					rtn="扣减成功";
				}
	        }
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    } finally {
	        lock.unlock();
	    }
	    return CommonResult.success(rtn);
	}
	
	@ApiOperation("无锁下单")
	@RequestMapping(value = "/nolock", method = RequestMethod.GET)
	public CommonResult<?> nolock(@RequestParam String id) {
		String rtn="库存不足";
		String productKey=Constants.CACHE_MALL_PRUDUCT+id;
    	int stock = Convert.toInt(redisTemplate.opsForValue().get(productKey),0);
		if (stock > 0) {
			int realStock = stock - 1;
			redisTemplate.opsForValue().set(productKey, realStock);
			rtn="扣减成功";
		}
	    return CommonResult.success(rtn);
	}
	
	@ApiOperation("增加库存")
	@RequestMapping(value = "/stock", method = RequestMethod.GET)
	public CommonResult<?> stock(@RequestParam String id) {
		String productKey=Constants.CACHE_MALL_PRUDUCT+id;
		redisTemplate.opsForValue().set(productKey, 30);
	    return CommonResult.success(true);
	}
	
	@ApiOperation("查询库存")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public CommonResult<?> query(@RequestParam String id) {
		String productKey=Constants.CACHE_MALL_PRUDUCT+id;
	    return CommonResult.success(redisTemplate.opsForValue().get(productKey));
	}

}
