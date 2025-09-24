package net.xzh.xss.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.xss.domain.LoginUser;

/**
 * 测试
 */
@RestController
public class ApiController {

	/**
	 * 过滤表单参数
	 * 
	 */
	@RequestMapping("/api")
	public String api(String msg) {
		return msg;
	}
	
	/**
	 * 过滤JSON参数
	 * 
	 */
	@PostMapping("/login")
	public String login(@RequestBody LoginUser form) {
		return form.getLoginName()+"-"+form.getPwd();
	}

	/**
	 * 不过滤表单参数
	 * 
	 * @param name
	 * @param msg
	 */
	@RequestMapping("/ignore/api")
	public String api2(String msg) {
		return msg;
	}

	/**
	 * 不过滤JSON参数
	 * 
	 */
	@PostMapping("/ignore/login")
	public String login2(@RequestBody LoginUser form) {
		return form.getLoginName()+"-"+form.getPwd();
	}
	
}