package net.xzh.dubbo.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.dubbo.common.model.CommonResult;
import net.xzh.dubbo.service.TicketService;

/**
 * dubbo客戶端
 * 
 * @author Administrator
 *
 */
@Api(tags = "dubbo客戶端")
@RestController
public class IndexlController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexlController.class);
	
//	@DubboReference(url = "${dubbo.testurl}")
	@DubboReference
	TicketService ticketService;

	@ApiOperation("test")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public CommonResult test(@RequestParam String userName) {
		LOGGER.info("hello,{}",userName);
		return CommonResult.success(ticketService.getTicket(userName));
	}

}