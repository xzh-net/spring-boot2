package net.xzh.etl.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.etl.repository.KettleService;

/**
 * 调用任务
 * 
 */
@RestController
public class EtlController {

	@Autowired
	private KettleService ks;

	/**
	 * 调用过程
	 * @param P_NAME
	 * @param P_YEAR
	 * @return
	 */
	@RequestMapping(value = "/callProc", method = RequestMethod.GET)
	public String callProc(@RequestParam(required = false) String P_NAME,
			@RequestParam(required = false) String P_YEAR) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("P_NAME", P_NAME);
		params.put("P_YEAR", P_YEAR);
		ks.runKtr("test.ktr", params, null);
		return "调用成功";
	}
	
	/**
	 * 调用job
	 * @param P_NAME
	 * @param P_YEAR
	 * @return
	 */
	@RequestMapping(value = "/callJob", method = RequestMethod.GET)
	public String callJob(@RequestParam(required = false) String P_NAME,
			@RequestParam(required = false) String P_YEAR) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("P_NAME", P_NAME);
		params.put("P_YEAR", P_YEAR);
		ks.runKjb("testjob.kjb", params, null);
		return "调用成功";
	}

}