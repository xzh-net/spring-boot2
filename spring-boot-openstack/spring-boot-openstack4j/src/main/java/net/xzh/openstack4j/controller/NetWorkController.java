package net.xzh.openstack4j.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.openstack4j.common.model.CommonResult;

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
	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public CommonResult<?> network() {
		return CommonResult.success(OSClient().networking().network().list());
	}

}