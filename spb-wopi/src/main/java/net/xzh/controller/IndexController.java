package net.xzh.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.xzh.i18n.I18nMessages;

@Controller
public class IndexController {

	/**
	 * 
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "/dashboard")
	@ResponseBody
	public ModelAndView login(HttpSession session, ModelAndView modelAndView) {
		modelAndView.addObject("welcome", I18nMessages.getMessage("welcome"));
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}

	@RequestMapping(value = "/login")
	@ResponseBody()
	public String login(String loginName, HttpSession session) {
		session.setAttribute("user", loginName);
		return "";
	}

	@RequestMapping(value = "/logout")
	@ResponseBody()
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "";
	}
}