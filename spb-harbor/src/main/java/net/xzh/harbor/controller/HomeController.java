package net.xzh.harbor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试推送镜像
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