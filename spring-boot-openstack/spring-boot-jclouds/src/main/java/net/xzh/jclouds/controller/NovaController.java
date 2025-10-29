package net.xzh.jclouds.controller;

import java.util.Set;

import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
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
	 * 查询区域
	 * @return
	 */
	@RequestMapping(value = "/regions", method = RequestMethod.GET)
	public Set<String> regions() {
		return novaApi().getConfiguredRegions();
	}
	
	/**
	 * 查询实例类型
	 * @return
	 */
	@RequestMapping(value = "/flavors", method = RequestMethod.GET)
	public Set<? extends Hardware> flavors() {
		return computeService().listHardwareProfiles();
	}
	
	/**
	 * 查询镜像
	 * @return
	 */
	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public Set<? extends Image> images() {
		return computeService().listImages();
	}

}