package net.xzh.harbor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 镜像推送后首页测试
 * 
 * @author Administrator
 *
 */
@RestController
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return System.currentTimeMillis() + "";
	}
	
}