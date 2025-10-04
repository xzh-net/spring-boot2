package net.xzh.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.service.UmsMemberService;

/**
 * 验证码
 */

@Tag(name = "登录管理", description = "登录管理")
@RestController
public class UmsMemberController {

	@Autowired
	private UmsMemberService memberService;

	
	@Operation(summary = "获取验证码", description = "有库存+1，没有则初始化")
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public CommonResult<?> generateCode(@RequestParam String telephone) {
		String identitycode = memberService.captcha(telephone);
		return CommonResult.success(identitycode);
	}

	@Operation(summary = "验证码登录", description = "验证码登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public CommonResult<?> login(@RequestParam String telephone, @RequestParam String code) {
		memberService.loginByCaptcha(telephone, code);
		return CommonResult.success("登录成功");
	}

}
