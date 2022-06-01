package net.xzh.dubbo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.dubbo.common.model.CommonResult;

/**
 * dubbo服务端
 * 
 * @author Administrator
 *
 */
@Api(tags = "dubbo服务端")
@RestController
public class IndexlController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexlController.class);
	 
	@ApiOperation("test")
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public CommonResult test(@RequestParam String id) {
		LOGGER.info("test,{}",id);
		return CommonResult.success(id);
	}

}