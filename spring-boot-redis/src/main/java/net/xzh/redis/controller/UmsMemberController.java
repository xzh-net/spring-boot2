package net.xzh.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.service.UmsMemberService;

/**
 * 验证码管理 update by xcg on 2024/9/29
 */
@Controller
@Api(tags = "验证码管理")
@RequestMapping("/sso")
public class UmsMemberController {
	@Autowired
	private UmsMemberService memberService;

	@ApiOperation("获取验证码")
	@RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<?> getAuthCode(@RequestParam String telephone) {
		String identitycode = memberService.generateAuthCode(telephone);
		return CommonResult.success(identitycode);
	}

	@ApiOperation("判断验证码是否正确")
	@RequestMapping(value = "/verifyAuthCode", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<?> updatePassword(@RequestParam String telephone, @RequestParam String identitycode) {
		if (StringUtils.isEmpty(identitycode)) {
			return CommonResult.failed("请输入验证码");
		}
		return CommonResult.success(memberService.verifyAuthCode(telephone, identitycode));
	}
}
