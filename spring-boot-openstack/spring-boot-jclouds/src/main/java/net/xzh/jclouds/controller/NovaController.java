package net.xzh.jclouds.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.jclouds.common.model.CommonResult;

/**
 * 计算管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "计算管理")
@RestController
public class NovaController extends BaseController {
	
	@ApiOperation("获取区域")
	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public CommonResult<?> regions() {
		return CommonResult.success(novaApi().getConfiguredRegions());
	}
	
}