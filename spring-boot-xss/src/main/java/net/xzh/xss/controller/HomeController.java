package net.xzh.xss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * 
 * @author Administrator
 *
 */
@RestController
public class HomeController {
	/**
	 * 
	 * @param name
	 * @param content
	 */
	@RequestMapping("/api/xss")
    public void xssApiTest(String name, String content) {
        System.out.println("api->name:"+name);
        System.out.println("api->content:"+content);
    }
    /**
     * @param name
     * @param content
     */
    @RequestMapping("/ui/xss")
    public void xssUiTest(String name, String content) {
        System.out.println("ui->name:"+name);
        System.out.println("ui->content:"+content);
    }

}