package net.xzh.redis.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.convert.Convert;
import net.xzh.redis.common.constant.Constants;
import net.xzh.redis.service.RedisService;
import net.xzh.redis.service.UmsMemberService;

/**
 * 会员管理Service实现类
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public String generatePhoneCode(String telephone) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			sb.append(random.nextInt(10));
		}
		// 验证码绑定手机号并存储到redis
		redisService.set(Constants.CACHE_AUTH_PHONE + telephone, sb.toString(), 120);
		return sb.toString();
	}

	// 对输入的验证码进行校验
	@Override
	public String extractPhoneCode(String telephone) {
		return Convert.toStr(redisService.get(Constants.CACHE_AUTH_PHONE + telephone));
	}

}
