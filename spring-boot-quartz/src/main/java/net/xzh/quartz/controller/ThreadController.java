package net.xzh.quartz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.quartz.service.ThreadService;

/**
 * 自定义线程池
 * 
 * @author Administrator
 *
 */
@RestController
public class ThreadController {
	
	@Autowired
	private ThreadService threadService;
	

	
	/**
	 * 同步调用
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sync/{id}", method = RequestMethod.GET)
	public String sync(@PathVariable(name = "id") String id) {
		threadService.sync();
		return "操作成功";
	}
	
	
	/**
	 * 异步默认
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/asyncDefault/{id}", method = RequestMethod.GET)
	public String asyncDefault(@PathVariable(name = "id") String id) {
		threadService.asyncDefault();
		return "操作成功";
	}

	
	/**
	 * 异步自定义
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/asyncCustom/{id}", method = RequestMethod.GET)
	public String asyncCustom(@PathVariable(name = "id") String id) {
		threadService.asyncCustom();
		return "操作成功";
	}


}