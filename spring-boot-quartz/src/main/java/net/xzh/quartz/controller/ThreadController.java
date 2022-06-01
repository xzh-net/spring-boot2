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
 * 自定义线程池 aop环绕实现日志插入
 * 
 * @author Administrator
 *
 */
@Api(tags = "自定义线程池")
@RestController
public class ThreadController {
	
	@Autowired
	private ThreadService threadService;
	

	@ApiOperation("线程异步调试")
	@RequestMapping(value = "/async/{id}", method = RequestMethod.GET)
	public CommonResult<Long> users(@PathVariable(name = "id") String id) {
		threadService.test();
		threadService.asyncTest();
		threadService.asyncExampleTest();
		return CommonResult.success(System.currentTimeMillis());
	}

}