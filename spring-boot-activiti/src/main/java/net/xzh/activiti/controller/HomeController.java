package net.xzh.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xzh.activiti.model.Menu;
import net.xzh.activiti.service.MenuService;

@Controller
public class HomeController {

	@Resource
	private MenuService menuService;

	@GetMapping("/home")
	public String index(Model model) {
		List<Menu> menus = menuService.selectMenuTree();
		model.addAttribute("menus", menus);
		return "home";
	}

	@GetMapping("/welcome")
	public String welcome(Model model) {
		model.addAttribute("userCount", 1);
		model.addAttribute("roleCount", 2);
		model.addAttribute("menuCount", 3);
		model.addAttribute("loginLogCount", 4);
		model.addAttribute("sysLogCount", 5);
		model.addAttribute("userOnlineCount", 6);
		return "welcome";
	}

	@GetMapping("/weekLoginCount")
	@ResponseBody
	public List<Integer> recentlyWeekLoginCount() {
		List<Integer> rtn = new ArrayList<Integer>();
		rtn.add(22);
		rtn.add(4);
		rtn.add(31);
		rtn.add(90);
		rtn.add(11);
		rtn.add(22);
		rtn.add(9);
		return rtn;
	}
}
