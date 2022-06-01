package net.xzh.chat.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统登录
 * @author CR7
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request) {
		String loginName = request.getParameter("loginName");
		if (loginName == null || loginName.equals("")) {
			return "index";
		} else {
			request.setAttribute("loginName", loginName);
			return "chat";
		}
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
