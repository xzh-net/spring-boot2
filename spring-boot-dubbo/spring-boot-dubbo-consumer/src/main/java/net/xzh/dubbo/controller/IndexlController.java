package net.xzh.dubbo.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.dubbo.service.UserService;

/**
 * dubbo客户端
 * @author xzh
 *
 */
@RestController
public class IndexlController {
	
	/**
	 * 直连服务端地址
	 */
	@DubboReference(url = "${dubbo.testurl}")
	UserService userService2;
	
	/**
	 * 从注册中心获取地址
	 */
	@DubboReference
	UserService userService;

	@GetMapping(value = "/getUser")
	public String getUser(@RequestParam String userName) {
		return userService.getUserName(userName);
	}
	
	@GetMapping(value = "/getUser2")
	public String getUser2(@RequestParam String userName) {
		return userService2.getUserName(userName);
	}

}