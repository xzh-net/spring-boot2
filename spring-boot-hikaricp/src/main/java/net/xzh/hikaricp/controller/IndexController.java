package net.xzh.hikaricp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.hikaricp.common.model.CommonResult;
import net.xzh.hikaricp.mapper.UmsAdminMapper;
import net.xzh.hikaricp.model.UmsAdmin;

/**
 * 测试
 * 
 * @author Administrator
 *
 */
@Api(tags = "用户管理")
@RestController
public class IndexController {
	@Autowired
	private UmsAdminMapper umsAdminMapper;

	@ApiOperation("获取指定id的用户详情")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public CommonResult<UmsAdmin> user(@PathVariable(value = "id") Long id) {
		return CommonResult.success(umsAdminMapper.selectByPrimaryKey(id));
	}

}