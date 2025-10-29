package net.xzh.openstack4j.controller;

import java.util.List;

import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计算管理
 * 
 * @author Administrator
 *
 */
@RestController
public class NovaController extends BaseController {
	

	/**
	 * 获取token
	 * @return
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String token() {
		return OSClient().getToken().getId();
	}
	
	/**
	 * 实例类型
	 * @return
	 */
	@RequestMapping(value = "/flavors", method = RequestMethod.GET)
	public List<? extends Flavor> flavors() {
		return OSClient().compute().flavors().list();
	}
	
	/**
	 * 查询镜像
	 * @return
	 */
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public List<? extends Image> images() {
//		OSClient().imagesV2().list()
		return OSClient().compute().images().list();
	}

}