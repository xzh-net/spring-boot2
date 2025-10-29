package net.xzh.jclouds.controller;

import java.util.Arrays;
import java.util.Set;

import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.TemplateOptions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.jclouds.model.VirtualMachine;

/**
 * 实例管理
 * 
 * @author Administrator
 *
 */
@RestController
public class InstanceController extends BaseController {

	/**
	 * 新建实例
	 * @param vm
	 * @return
	 */
	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	public Set<? extends NodeMetadata> instanceAdd(@RequestBody VirtualMachine vm) {
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

		return nodes;
	}

	/**
	 * 查询实例
	 * @return
	 */
	@RequestMapping(value = "/instances", method = RequestMethod.GET)
	public Set<? extends ComputeMetadata> instances() {
		return computeService().listNodes();
	}

	/**
	 * 实例详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.GET)
	public NodeMetadata instanceDetail(@PathVariable String id) {
		return computeService().getNodeMetadata("RegionOne/" + id);
	}

	/**
	 * 销毁实例
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.DELETE)
	public String instanceRemove(@PathVariable String id) {
		computeService().destroyNode("RegionOne/" + id);
		return "success";
	}

	/**
	 * 重启实例
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instance/reboot/{id}", method = RequestMethod.GET)
	public String instanceReboot(@PathVariable String id) {
		computeService().rebootNode("RegionOne/" + id);
		return "success";
	}

}