package net.xzh.hikaricp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.hikaricp.common.model.CommonResult;
import net.xzh.hikaricp.mapper.UmsAdminMapper;
import net.xzh.hikaricp.model.UmsAdmin;

/**
 * 用户管理
 * 
 * @author Administrator
 *
 */
@Tag(name = "用户管理", description = "用户管理相关的API")
@RestController
public class IndexController {
	@Autowired
	private UmsAdminMapper umsAdminMapper;

	@Operation(summary = "获取指定id的用户详情", description = "获取指定id的用户详情")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public CommonResult<UmsAdmin> user(@PathVariable(value = "id") Long id) {
		return CommonResult.success(umsAdminMapper.selectByPrimaryKey(id));
	}

}