package net.xzh.openstack4j.controller;

import java.util.Arrays;
import java.util.List;

import org.openstack4j.api.Builders;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.RebootType;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.VNCConsole;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	 * @param instanceName
	 * @param networks
	 * @param image
	 * @param flavor
	 * @return
	 */
	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	public String instanceAdd(@RequestParam String instanceName, @RequestParam String networks,
			@RequestParam String image, @RequestParam String flavor) {
		ServerCreate request = Builders.server().name(instanceName + System.currentTimeMillis())
				.networks(Arrays.asList(networks)).image(image).flavor(flavor)
				// .keypairName("keypairName")
				.build();
		Server server = OSClient().compute().servers().bootAndWaitActive(request, 600);
		return server.getId();
	}
	
	/**
	 * 查询实例
	 * @return
	 */
	@RequestMapping(value = "/instances", method = RequestMethod.GET)
	public List<? extends Server> instances() {
		return OSClient().compute().servers().list();
	}
	

	/**
	 * 实例详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.GET)
	public Server instanceDetail(@PathVariable String id) {
		return OSClient().compute().servers().get(id);
	}
	
	/**
	 * 获取vnc地址
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/vncConsole/{id}", method = RequestMethod.GET)
	public VNCConsole vncConsole(@PathVariable String id) {
		return OSClient().compute().servers().getVNCConsole(id, null);
	}

	/**
	 * 删除实例
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instance/{id}", method = RequestMethod.DELETE)
	public ActionResponse instanceRemove(@PathVariable String id) {
		return OSClient().compute().servers().delete(id);
	}

	/**
	 * 重启实例
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/instance/reboot/{id}", method = RequestMethod.GET)
	public ActionResponse instanceReboot(@PathVariable String id) {
		return OSClient().compute().servers().reboot(id, RebootType.SOFT);
	}

	/**
	 * 销毁实例
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/node/destroy/{id}", method = RequestMethod.GET)
	public ActionResponse nodeDestroy(@PathVariable String id) {
		return OSClient().compute().servers().action(id, Action.FORCEDELETE);
	}

}