package net.xzh.openstack4j.controller;

import java.util.Arrays;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.openstack.OSFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.openstack4j.common.model.CommonResult;

/**
 * 首页
 * 
 * @author Administrator
 *
 */
@Api(tags = "OpenStack4j管理")
@RestController
public class ApiController {

	public static OSClientV3 getConnection() {
		String endpoint = "http://172.17.19.30:5000/v3";
		String userName = "xuchaoguo";
		String password = "000000";
		String tenantId = "1a12c595513d4127ab5426381f14a2a1";
		String domainId = "default";
		OSClientV3 os = OSFactory.builderV3().endpoint(endpoint)
				.credentials(userName, password, Identifier.byId(domainId)).scopeToProject(Identifier.byId(tenantId))
				.authenticate();
		return os;
	}

	@ApiOperation("获取token")
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public CommonResult<?> token() {
		return CommonResult.success(getConnection().getToken().getId());
	}

	@ApiOperation("查询网络列表")
	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public CommonResult<?> network() {
		return CommonResult.success(getConnection().networking().network().list());
	}

	@ApiOperation("查询规格类型列表")
	@RequestMapping(value = "/flavors", method = RequestMethod.GET)
	public CommonResult<?> flavors() {
		return CommonResult.success(getConnection().compute().flavors().list());
	}

	@ApiOperation("查询镜像列表")
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public CommonResult<?> images() {
		return CommonResult.success(getConnection().compute().images().list());
	}

	@ApiOperation("查询实例列表")
	@RequestMapping(value = "/instances", method = RequestMethod.GET)
	public CommonResult<?> instances() {
		return CommonResult.success(getConnection().compute().servers().list());
	}

	@ApiOperation("新建实例")
	@RequestMapping(value = "/instances", method = RequestMethod.POST)
	public CommonResult<?> instancesAdd(@RequestParam String instanceName,@RequestParam String networks,@RequestParam String image,@RequestParam String flavor) {
		ServerCreate request = Builders.server().name(instanceName + System.currentTimeMillis())
				.networks(Arrays.asList(networks))
				.image(image)
				.flavor(flavor)
				//.keypairName("keypairName")
				.build();
		Server server = getConnection().compute().servers().bootAndWaitActive(request, 600);
		return CommonResult.success(server.getId());
	}

	@ApiOperation("查询实例详情")
	@RequestMapping(value = "/instances/{id}", method = RequestMethod.GET)
	public CommonResult<?> instancesDetails(@PathVariable String id) {
		return CommonResult.success(getConnection().compute().servers().get(id));
	}

	@ApiOperation("删除实例")
	@RequestMapping(value = "/instances/{id}", method = RequestMethod.DELETE)
	public CommonResult<?> instancesRemove(@PathVariable String id) {
		return CommonResult.success(getConnection().compute().servers().delete(id));
	}

}