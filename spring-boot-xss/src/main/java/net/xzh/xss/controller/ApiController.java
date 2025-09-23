package net.xzh.xss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
public class ApiController {
	
	/**
	 * 测试过滤
	 * @param name
	 * @param msg
	 */
	@RequestMapping("/api")
    public void xssApiTest(String name, String msg) {
        System.out.println("api->name:"+name);
        System.out.println("api->msg:"+msg);
    }
    /**
     * 测试不过滤
     * @param name
     * @param msg
     */
    @RequestMapping("/ui")
    public void xssUiTest(String name, String msg) {
        System.out.println("ui->name:"+name);
        System.out.println("ui->msg:"+msg);
    }

}