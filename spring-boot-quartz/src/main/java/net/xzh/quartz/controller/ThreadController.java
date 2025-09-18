package net.xzh.quartz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.quartz.common.model.CommonResult;
import net.xzh.quartz.service.ThreadService;

/**
 * 自定义线程池
 * 
 * @author Administrator
 *
 */
@Api(tags = "线程池")
@RestController
public class ThreadController {
	
	@Autowired
	private ThreadService threadService;
	

	@ApiOperation("同步")
	@RequestMapping(value = "/sync/{id}", method = RequestMethod.GET)
	public CommonResult<Long> sync(@PathVariable(name = "id") String id) {
		threadService.sync();
		return CommonResult.success(System.currentTimeMillis());
	}
	
	@ApiOperation("异步默认")
	@RequestMapping(value = "/asyncDefault/{id}", method = RequestMethod.GET)
	public CommonResult<Long> asyncDefault(@PathVariable(name = "id") String id) {
		threadService.asyncDefault();
		return CommonResult.success(System.currentTimeMillis());
	}

	
	@ApiOperation("异步自定义")
	@RequestMapping(value = "/asyncCustom/{id}", method = RequestMethod.GET)
	public CommonResult<Long> asyncCustom(@PathVariable(name = "id") String id) {
		threadService.asyncCustom();
		return CommonResult.success(System.currentTimeMillis());
	}


}