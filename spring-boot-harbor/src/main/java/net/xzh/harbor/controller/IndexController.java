package net.xzh.harbor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class IndexController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@ApiOperation("系统登录")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public CommonResult sendTextMail() {
		return CommonResult.success(System.currentTimeMillis());
	}
}