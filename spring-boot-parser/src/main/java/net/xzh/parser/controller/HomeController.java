package net.xzh.parser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 * 
 * @author Administrator
 */
@RestController
public class HomeController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return System.currentTimeMillis() + "";
	}
}