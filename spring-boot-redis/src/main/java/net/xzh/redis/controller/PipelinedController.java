package net.xzh.redis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;

/**
 * 管道测试
 * 
 * @author Administrator
 *
 */
@Api(tags = "管道操作")
@RestController
@RequestMapping("/pipe")
public class PipelinedController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ApiOperation("string管道添加")
	@RequestMapping(value = "/stringSet", method = RequestMethod.GET)
	public CommonResult<?> stringSet(@RequestParam String id) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("");
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 1000; i++) {
			map.put("pipe:str:" + id + ":" + i, "我们value" + i);
		}
		RedisSerializer serializer = redisTemplate.getStringSerializer();
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				map.forEach((key, value) -> {
					connection.set(serializer.serialize(key), serializer.serialize(value), Expiration.seconds(6000),
							RedisStringCommands.SetOption.UPSERT);
				});
				return null;
			}
		}, serializer);
		stopWatch.stop();
		return CommonResult.success(stopWatch.getTotalTimeMillis());
	}

	@ApiOperation("string管道获取")
	@RequestMapping(value = "/stringGet", method = RequestMethod.GET)
	public CommonResult<?> stringGet(@RequestParam String id) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 1000; i++) {
			map.put("pipe:str:" + id + ":" + i, i + "");
		}
		RedisSerializer serializer = redisTemplate.getStringSerializer();
		List<Object> redisResult = redisTemplate.executePipelined(new RedisCallback<String>() {
			// 自定义序列化
			@Override
			public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
				map.forEach((key, value) -> {
					redisConnection.get(serializer.serialize(key));
				});
				return null;
			}
		}, serializer);
		return CommonResult.success(redisResult);
	}

	@ApiOperation("json管道添加")
	@RequestMapping(value = "/jsonSet", method = RequestMethod.GET)
	public CommonResult<?> jsonSet(String id) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("");
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 1000; i++) {
			map.put("pipe:json:" + id + ":" + i, newJson(i));
		}
		RedisSerializer serializer =redisTemplate.getHashValueSerializer();
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				map.forEach((key, value) -> {
					connection.set(serializer.serialize(key), serializer.serialize(value), Expiration.seconds(6000),
							RedisStringCommands.SetOption.UPSERT);
				});
				return null;
			}
		}, serializer);
		stopWatch.stop();
		return CommonResult.success(stopWatch.getTotalTimeMillis());
	}

	@ApiOperation("json管道获取")
	@RequestMapping(value = "/jsonGet", method = RequestMethod.GET)
	public CommonResult<?> jsonGet(String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < 1000; i++) {
			map.put("pipe:json:" + id + ":" + i, newJson(i));
		}
		RedisSerializer serializer = redisTemplate.getHashValueSerializer();
		List<Object> redisResult = redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				map.forEach((key, value) -> {
					connection.get(serializer.serialize(key));
				});
				return null;
			}
		}, serializer);
		return CommonResult.success(redisResult);
	}

	private ArrayList<Object> newJson(int i) {
		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add("xzh" + i);
		msg.add(20220120);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "xuzhihao_" + i);
		map.put("idcard", 210212);
		map.put("memo", "我是个大盗贼_"+i);
		msg.add(map);
		return msg;
	}

}