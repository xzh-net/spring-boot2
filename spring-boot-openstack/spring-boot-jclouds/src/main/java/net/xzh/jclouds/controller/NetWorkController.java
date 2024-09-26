package net.xzh.jclouds.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.jclouds.common.model.CommonResult;

/**
 * 网络管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "网络")
@RestController
public class NetWorkController extends BaseController {

	@ApiOperation("查询")
	@RequestMapping(value = "/network/{id}", method = RequestMethod.GET)
	public CommonResult<?> network(@PathVariable String id) {
		return CommonResult.success(neutronApi().getNetworkApi("RegionOne").get(id));
	}
}