package net.xzh.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.api.annotation.ApiVersion;

/**
 * 订单管理
 * 
 * @author xzh
 *
 */
@ApiVersion(2)
@RestController
@RequestMapping("api/{version}/order")
public class OrderV2Controller {
	
	@RequestMapping(value = "/save/{orderId}", method = RequestMethod.GET)
    public String saveOrderById(@PathVariable int orderId){
        return "v2订单保存成功" + orderId;
    }
	
	@RequestMapping(value = "/delete/{orderId}", method = RequestMethod.GET)
	public String deleteOrderById(@PathVariable int orderId) {
		return "v2订单删除成功" + orderId;
	}
	
}