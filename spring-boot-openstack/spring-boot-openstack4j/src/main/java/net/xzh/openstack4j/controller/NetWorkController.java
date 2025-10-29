package net.xzh.openstack4j.controller;

import java.util.List;

import org.openstack4j.model.network.Network;
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
	 * @return
	 */
	@RequestMapping(value = "/network", method = RequestMethod.GET)
	public List<? extends Network> network() {
		return OSClient().networking().network().list();
	}

}