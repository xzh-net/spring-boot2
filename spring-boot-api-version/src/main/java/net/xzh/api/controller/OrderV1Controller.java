package net.xzh.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单管理
 * 
 * @author xzh
 *
 */
@RestController
@RequestMapping("api/{version}/order")
public class OrderV1Controller {
	
    @RequestMapping(value = "/save/{orderId}", method = RequestMethod.GET)
    public String saveOrderById(@PathVariable int orderId){
        return "v1订单保存成功" + orderId;
    }

    @GetMapping("/search/{orderId}")
    public String searchOrderById(@PathVariable int orderId){
        return "v1获取订单详情" + orderId;
    }
}
