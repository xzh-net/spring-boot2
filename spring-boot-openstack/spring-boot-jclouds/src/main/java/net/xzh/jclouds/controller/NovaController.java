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
@Api(tags = "计算")
@RestController
public class NovaController extends BaseController {
	
	@ApiOperation("区域")
	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public CommonResult<?> regions() {
		return CommonResult.success(novaApi().getConfiguredRegions());
	}
	
	@ApiOperation("实例类型")
	@RequestMapping(value = "/flavors", method = RequestMethod.GET)
	public CommonResult<?> flavors() {
		return CommonResult.success(computeService().listHardwareProfiles());
	}
	
	@ApiOperation("镜像")
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public CommonResult<?> images() {
		return CommonResult.success(computeService().listImages());
	}

}