package net.xzh.jclouds.controller;

import java.util.Arrays;
import java.util.Set;

import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.TemplateOptions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.jclouds.common.model.CommonResult;

/**
 * 实例管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "实例管理")
@RestController
public class InstanceController extends BaseController {

	@ApiOperation("新建实例")
	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	public CommonResult<?> instanceAdd(@RequestParam String instanceName, @RequestParam String networks,
			@RequestParam String image, @RequestParam String flavor) {
//		对比openstack4j创建实例参数
//		ServerCreate request = Builders.server().name("test_instances" + System.currentTimeMillis())
//				.networks(Arrays.asList("bf170898-c3ce-4f05-b6ce-8e3390a6e62b"))
//				.image("ed0152b5-8a4f-456e-b268-287223b5c310")//
//				.flavor("lif.flavor.id").keypairName("lif-key").build();

		// 自定义参数
		TemplateOptions options = TemplateOptions.Builder
				.networks(Arrays.asList("bf170898-c3ce-4f05-b6ce-8e3390a6e62b"));
		// 实例模板
		Template template = computeService().templateBuilder().locationId("RegionOne")
				.imageId("RegionOne/ed0152b5-8a4f-456e-b268-287223b5c310").hardwareId("RegionOne/lif.flavor.id")
				.options(options).build();
		Set<? extends NodeMetadata> nodes = null;
		try {
			nodes = computeService().createNodesInGroup(instanceName, 1, template);
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