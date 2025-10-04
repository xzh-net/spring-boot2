package net.xzh.redis.service.impl;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import net.xzh.redis.common.constant.Constants;
import net.xzh.redis.common.exception.ApiException;
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
	public String captcha(String telephone) {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(Constants.CACHE_PHONE_CODE_LENGTH);

		for (int i = 0; i < Constants.CACHE_PHONE_CODE_LENGTH; i++) {
			sb.append(random.nextInt(10));
		}
		String phoneCode = sb.toString();
		String cacheKey = Constants.CACHE_PHONE_CODE + telephone;

		// 验证码绑定手机号并存储到Redis
		redisService.set(cacheKey, sb.toString(), 120);
		return phoneCode;
	}

	// 对输入的验证码进行校验
	@Override
	public void loginByCaptcha(String telephone, String code) {
		if (StrUtil.isEmpty(code)) {
			throw new ApiException("请输入验证码");
		}
		String cacheKey = Constants.CACHE_PHONE_CODE + telephone;
		String identitycode = Convert.toStr(redisService.get(cacheKey));
		if (!code.equals(identitycode)) {
			throw new ApiException("验证码错误");
		}
		redisService.del(cacheKey);
	}

}
