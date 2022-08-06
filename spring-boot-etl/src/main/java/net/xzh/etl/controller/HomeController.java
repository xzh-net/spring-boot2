package net.xzh.etl.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.etl.common.model.CommonResult;
import net.xzh.etl.repository.KettleService;

/**
 * 调用任务
 * 
 * @author Administrator
 *
 */
@Api(tags = "测试接口")
@RestController
public class HomeController {

	@Autowired
	private KettleService ks;

	@ApiOperation("调用过程")
	@RequestMapping(value = "/callProc", method = RequestMethod.GET)
	public CommonResult<?> callProc(@RequestParam(required = false) String P_NAME,
			@RequestParam(required = false) String P_YEAR) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("P_NAME", P_NAME);
		params.put("P_YEAR", P_YEAR);
		ks.runKtr("test.ktr", params, null);
		return CommonResult.success(System.currentTimeMillis());
	}
	
	@ApiOperation("调用job")
	@RequestMapping(value = "/callJob", method = RequestMethod.GET)
	public CommonResult<?> callJob(@RequestParam(required = false) String P_NAME,
			@RequestParam(required = false) String P_YEAR) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("P_NAME", P_NAME);
		params.put("P_YEAR", P_YEAR);
		ks.runKjb("testjob.kjb", params, null);
		return CommonResult.success(System.currentTimeMillis());
	}

}