package net.xzh.mq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.mq.common.model.CommonResult;
import net.xzh.mq.delay.DelaySender;
import net.xzh.mq.dxl.DxlSender;
import net.xzh.mq.manual.ManualSender;

/**
 * Created by macro on 2020/5/19.
 */
@Api(tags = "Rabbit测试")
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private ManualSender manualSender;
    @Autowired
    private DelaySender delaySender;
    @Autowired
    private DxlSender dxlSender;
    
    @ApiOperation("手动确认")
    @RequestMapping(value = "/Manual", method = RequestMethod.GET)
    public CommonResult Manual(@RequestParam String orderId) {
    	manualSender.send(orderId);
    	return CommonResult.success(orderId);
	}
    
    @ApiOperation("延迟消息")
    @RequestMapping(value = "/delay", method = RequestMethod.GET)
    public CommonResult delay(@RequestParam String orderId) {
    	delaySender.send(orderId,5000L);
    	return CommonResult.success(orderId);
	}
    
    @ApiOperation("dxl消息")
    @RequestMapping(value = "/dxl", method = RequestMethod.GET)
    public CommonResult dxl(@RequestParam String orderId) {
    	dxlSender.send(orderId,5000L);
    	return CommonResult.success(null);
	}
}
