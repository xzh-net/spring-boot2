package net.xzh.redis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;

/**
 * 发布订阅测试
 * 
 * @author Administrator
 *
 */
@Api(tags = "发布订阅")
@RestController
@RequestMapping("/message")
public class PubSubController {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@ApiOperation("按主题")
    @RequestMapping(value = "/push", method = RequestMethod.GET)
    public CommonResult<?> push(@RequestParam String id) {
		redisTemplate.convertAndSend("redisChat", id + "room" );
		redisTemplate.convertAndSend("redisChat2", id + "room2");
		return CommonResult.success(1);
    }
    
    @ApiOperation("key失效过期")
    @RequestMapping(value = "/set", method = RequestMethod.GET)
	public CommonResult<?> set(String id) {
    	ArrayList<Object> msg = new ArrayList<Object>();
    	msg.add("xzh");
    	msg.add(20220120);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "xuzhihao");
		map.put("idcard", 210212);
		map.put("memo", "我是个大盗贼");
		msg.add(map);
		redisTemplate.opsForValue().set(id, msg, 30, TimeUnit.SECONDS);
		return CommonResult.success(redisTemplate.opsForValue().get(id));
	}
	
}