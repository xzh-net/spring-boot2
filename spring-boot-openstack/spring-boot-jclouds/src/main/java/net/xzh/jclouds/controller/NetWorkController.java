package net.xzh.jclouds.controller;

import org.jclouds.openstack.neutron.v2.domain.Network;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网络管理
 * 
 * @author Administrator
 *
 */
@RestController
public class NetWorkController extends BaseController {

	/**
	 * 查询网络
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/network/{id}", method = RequestMethod.GET)
	public Network network(@PathVariable String id) {
		return neutronApi().getNetworkApi("RegionOne").get(id);
	}
}