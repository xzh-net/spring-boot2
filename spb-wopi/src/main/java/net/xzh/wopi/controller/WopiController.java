package net.xzh.wopi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/wopi")
public class WopiController {

	@RequestMapping(value = "/index")
	public String index(HttpSession session, ModelAndView modelAndView) {
		return "欢迎";
	}

}