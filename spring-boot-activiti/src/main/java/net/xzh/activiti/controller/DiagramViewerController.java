package net.xzh.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 可视化前端控制器
 **/
@Controller
public class DiagramViewerController {
	@GetMapping("editor")
	public String test() {
		return "workflow/modeler";
	}
}
