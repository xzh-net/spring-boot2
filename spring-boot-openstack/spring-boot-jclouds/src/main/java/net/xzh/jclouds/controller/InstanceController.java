package net.xzh.jclouds.controller;

import java.util.Arrays;
import java.util.Set;

import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.TemplateOptions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.jclouds.common.model.CommonResult;
import net.xzh.jclouds.model.VirtualMachine;

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
	public CommonResult<?> instanceAdd(@RequestBody VirtualMachine vm) {
		// 自定义参数
		TemplateOptions options = TemplateOptions.Builder
				.networks(Arrays.asList(vm.getNetworks()));
		// 实例模板
		Template template = computeService().templateBuilder().locationId(vm.getRegionId())
				.imageId(vm.getRegionId() +"/"+ vm.getImageId()).hardwareId(vm.getRegionId()+"/"+vm.getHardwareId())
				.options(options).build();
		Set<? extends NodeMetadata> nodes = null;
		try {
			nodes = computeService().createNodesInGroup(vm.getServerName(), 1, template);
		} catch (RunNodesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return CommonResult.success(nodes);
	}

	@ApiOperation("查询实例")
	@RequestMapping(value = "/instances", method = RequestMethod.GET)
	public CommonResult<?> instances() {
		return CommonResult.success(computeService().listNodes());
	}

	@ApiOperation("实例详情")
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.GET)
	public CommonResult<?> instanceDetail(@PathVariable String id) {
		return CommonResult.success(computeService().getNodeMetadata("RegionOne/" + id));
	}

	@ApiOperation("销毁实例")
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.DELETE)
	public CommonResult<?> instanceRemove(@PathVariable String id) {
		computeService().destroyNode("RegionOne/" + id);
		return CommonResult.success(null);
	}

	@ApiOperation("重启实例")
	@RequestMapping(value = "/instance/reboot/{id}", method = RequestMethod.GET)
	public CommonResult<?> instanceReboot(@PathVariable String id) {
		computeService().rebootNode("RegionOne/" + id);
		return CommonResult.success(null);
	}

}