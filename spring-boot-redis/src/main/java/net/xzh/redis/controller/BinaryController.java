package net.xzh.redis.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;

/**
 * 管道测试
 * 
 * @author Administrator
 *
 */
@Api(tags = "二进制数据测试")
@RestController
@RequestMapping("/binary")
public class BinaryController {
	
	@Autowired
	private RedisTemplate<String, Object> serializeRedisTemplate;
	
	@ApiOperation("二进制set")
    @RequestMapping(value = "/byteSet", method = RequestMethod.GET)
	public CommonResult byteSet(@RequestParam String id) {
		ArrayList<HashMap<String, Object>> l = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", IdUtil.fastSimpleUUID());
			map.put("time", LocalDate.now());
			l.add(map);
		}
		serializeRedisTemplate.opsForValue().set(id, ObjectUtil.serialize(l));
		return CommonResult.success(1);
	}

    @ApiOperation("二进制get")
    @RequestMapping(value = "/byteGet", method = RequestMethod.GET)
	public CommonResult byteGet(@RequestParam String id) {
		byte[] bytes = (byte[]) serializeRedisTemplate.opsForValue().get(id);
		List<HashMap<String, Object>> rtn = ObjectUtil.unserialize(bytes);
		return CommonResult.success(rtn);
	}
	
}