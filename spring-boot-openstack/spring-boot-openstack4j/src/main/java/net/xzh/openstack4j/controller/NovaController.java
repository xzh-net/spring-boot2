package net.xzh.openstack4j.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.openstack4j.common.model.CommonResult;

/**
 * 计算管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "计算")
@RestController
public class NovaController extends BaseController {
	

	@ApiOperation("获取token")
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public CommonResult<?> token() {
		return CommonResult.success(OSClient().getToken().getId());
	}
	
	@ApiOperation("实例类型")
	@RequestMapping(value = "/flavors", method = RequestMethod.GET)
	public CommonResult<?> flavors() {
		return CommonResult.success(OSClient().compute().flavors().list());
	}
	
	@ApiOperation("镜像")
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public CommonResult<?> images() {
//		OSClient().imagesV2().list()
		return CommonResult.success(OSClient().compute().images().list());
	}

}