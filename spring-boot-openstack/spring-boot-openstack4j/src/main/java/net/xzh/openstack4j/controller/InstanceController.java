package net.xzh.openstack4j.controller;

import java.util.Arrays;

import org.openstack4j.api.Builders;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.openstack4j.common.model.CommonResult;

/**
 * 实例管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "实例")
@RestController
public class InstanceController extends BaseController {


	@ApiOperation("新建实例")
	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	public CommonResult<?> instanceAdd(@RequestParam String instanceName, @RequestParam String networks,
			@RequestParam String image, @RequestParam String flavor) {
		ServerCreate request = Builders.server().name(instanceName + System.currentTimeMillis())
				.networks(Arrays.asList(networks)).image(image).flavor(flavor)
				// .keypairName("keypairName")
				.build();
		Server server = OSClient().compute().servers().bootAndWaitActive(request, 600);
		return CommonResult.success(server.getId());
	}
	
	@ApiOperation("查询实例")
	@RequestMapping(value = "/instances", method = RequestMethod.GET)
	public CommonResult<?> instances() {
		return CommonResult.success(OSClient().compute().servers().list());
	}
	

	@ApiOperation("实例详情")
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.GET)
	public CommonResult<?> instanceDetail(@PathVariable String id) {
		return CommonResult.success(OSClient().compute().servers().get(id));
	}

	@ApiOperation("删除实例")
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.DELETE)
	public CommonResult<?> instanceRemove(@PathVariable String id) {
		return CommonResult.success(OSClient().compute().servers().delete(id));
	}

	@ApiOperation("重启实例")
	@RequestMapping(value = "/instance/reboot/{id}", method = RequestMethod.GET)
	public CommonResult<?> instanceReboot(@PathVariable String id) {
		return CommonResult.success(OSClient().compute().servers().reboot(id, RebootType.SOFT));
	}

	@ApiOperation("销毁实例")
	@RequestMapping(value = "/node/destroy/{id}", method = RequestMethod.GET)
	public CommonResult<?> nodeDestroy(@PathVariable String id) {
		return CommonResult.success(OSClient().compute().servers().action(id, Action.FORCEDELETE));
	}

}