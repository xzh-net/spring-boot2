package net.xzh.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.service.UmsMemberService;

/**
 * 验证码
 */
@Api(tags = "验证码")
@RestController
@RequestMapping("/sso")
public class UmsMemberController {
	@Autowired
	private UmsMemberService memberService;

	@ApiOperation("获取验证码")
	@RequestMapping(value = "/generateCode", method = RequestMethod.GET)
	public CommonResult<?> generateCode(@RequestParam String telephone) {
		String identitycode = memberService.generatePhoneCode(telephone);
		return CommonResult.success(identitycode);
	}

	@ApiOperation("登录验证")
	@RequestMapping(value = "/extractCode", method = RequestMethod.POST)
	public CommonResult<?> extractCode(@RequestParam String telephone, @RequestParam String identitycode) {
		if (StringUtils.isEmpty(identitycode)) {
			return CommonResult.failed("请输入验证码");
		}
		String extractCode=memberService.extractPhoneCode(telephone);
		if(identitycode.equals(extractCode)){
			return CommonResult.success("登录成功");
		}
		return CommonResult.failed("验证码错误");
	}
}
