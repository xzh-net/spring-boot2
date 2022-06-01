package net.xzh.redis.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.convert.Convert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;

/**
 * 分布式锁 实现方式：RedissonClient
 * 
 * @author Administrator
 *
 */
@Api(tags = "分布式锁")
@RestController
@RequestMapping("/lock")
public class LockController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LockController.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;// 来模拟数据库存
	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 库存超卖
	 * 
	 * @return
	 */
	@ApiOperation("库存超卖")
	@RequestMapping(value = "/deductStock", method = RequestMethod.GET)
	public CommonResult deductStock(@RequestParam String id) {
		String lockKey="stock"+id;
		RLock reentrantLock = redissonClient.getLock(id);
		try {
			reentrantLock.lock();
			int stock = Convert.toInt(stringRedisTemplate.opsForValue().get(lockKey),0);
			if (stock > 0) {
				int realStock = stock - 1;
				stringRedisTemplate.opsForValue().set(lockKey, realStock + "");
				LOGGER.info("扣减成功，剩余库存{}", realStock);
			} else {
				LOGGER.info("扣减失败，库存不足");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			reentrantLock.unlock();
		}
		return CommonResult.success(1);
	}

	/**
	 * 读锁readWriteLock
	 */
	@ApiOperation("读锁")
	@RequestMapping(value = "/readLock", method = RequestMethod.GET)
	public String readWriteLock(@RequestParam String id) {
		RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(id);
		try {
			readWriteLock.readLock().lock(10, TimeUnit.SECONDS);
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			readWriteLock.readLock().unlock();
		}
		return Thread.currentThread().getId() + " 读成功.";
	}

	/**
	 * 写锁readWriteLock
	 */
	@ApiOperation("写锁")
	@RequestMapping(value = "/writeLock", method = RequestMethod.GET)
	public String writeLock(@RequestParam String id) {
		RReadWriteLock readWriteLock = redissonClient.getReadWriteLock(id);
		try {
			readWriteLock.writeLock().lock(10, TimeUnit.SECONDS);
		} finally {
			readWriteLock.writeLock().unlock();
		}
		return Thread.currentThread().getId() + " 写成功.";
	}

}
