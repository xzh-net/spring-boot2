package net.xzh.wechat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页
 */
@RestController
public class IndexController {

	/**
	 * 跳转到首页
	 * 
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@GetMapping(value = "/index")
	public ModelAndView login(HttpSession session, ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		return modelAndView;
	}

	/**
	 * 登出
	 * 
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@PostMapping(value = "/logout")
	@ResponseBody
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "forward:/wechat/index";
	}

	
}
