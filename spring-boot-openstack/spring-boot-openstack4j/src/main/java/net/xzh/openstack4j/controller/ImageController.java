package net.xzh.openstack4j.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.openstack4j.common.model.CommonResult;

/**
 * 镜像管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "镜像管理")
@RestController
public class ImageController extends BaseController {
	
	@ApiOperation("查询列表v2")
	@RequestMapping(value = "/imagesV2", method = RequestMethod.GET)
	public CommonResult<?> imagesV2() {
		return CommonResult.success(OSClient().imagesV2().list());
	
	}
	
	@ApiOperation("查询列表")
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public CommonResult<?> images() {
		return CommonResult.success(OSClient().compute().images().list());
	
	}
}