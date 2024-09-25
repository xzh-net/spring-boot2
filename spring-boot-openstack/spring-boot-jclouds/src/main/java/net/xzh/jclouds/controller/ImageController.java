package net.xzh.jclouds.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.jclouds.common.model.CommonResult;

/**
 * 镜像管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "镜像管理")
@RestController
public class ImageController extends BaseController {
	
	@ApiOperation("查询镜像")
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public CommonResult<?> images() {
		return CommonResult.success(computeService().listImages());
	}

}