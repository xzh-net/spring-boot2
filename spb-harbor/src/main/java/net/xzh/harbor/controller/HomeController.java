package net.xzh.harbor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.harbor.common.model.CommonResult;

/**
 * 首页
 * 
 * @author Administrator
 *
 */
@Api(tags = "首页")
@RestController
public class HomeController {
	

	@ApiOperation("登录")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public CommonResult<?> index() {
		return CommonResult.success(System.currentTimeMillis());
	}
}