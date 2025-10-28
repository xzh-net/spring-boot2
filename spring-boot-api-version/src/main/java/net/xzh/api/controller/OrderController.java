package net.xzh.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.api.annotation.ApiVersion;

/**
 * 订单管理
 * 
 * @author xzh
 *
 */
@ApiVersion(1)  // 类级别版本
@RestController
@RequestMapping("api/{version}/order")
public class OrderController {
	
	//默认继承方法级别
    @GetMapping("/{id}")
    public String get(@PathVariable String id){
        return "v1查询订" + id;
    }
	
	
	@ApiVersion(3)
	@GetMapping("/{id}")
    public String get3(@PathVariable String id){
        return "v3查询订单" + id;
    }

	
}
