package net.xzh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.xzh.i18n.I18nMessages;

@Controller
public class IndexController {

	/**
	 * 我的桌面
	 */
	@GetMapping("/dashboard")
	public ModelAndView login(HttpSession session, ModelAndView modelAndView) {
		modelAndView.addObject("welcome", I18nMessages.getMessage("welcome"));
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}

	@PostMapping("/login")
	@ResponseBody()
	public String login(String loginName, HttpSession session) {
		session.setAttribute("user", loginName);
		return "success‌";
	}

	@PostMapping("/logout")
	@ResponseBody()
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "success‌";
	}
}