package net.xzh.jasypt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.jasypt.common.model.CommonResult;
import net.xzh.jasypt.mapper.UmsAdminMapper;
import net.xzh.jasypt.model.UmsAdmin;

/**
 * 用户管理
 * 
 * @author Administrator
 *
 */
@Tag(name = "用户管理", description = "用户管理相关的API")
@RestController
public class IndexController {
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Autowired
	private UmsAdminMapper umsAdminMapper;

	@Operation(summary = "获取指定id的用户详情", description = "获取指定id的用户详情")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public CommonResult<UmsAdmin> user(@PathVariable(value = "id") Long id) {
		return CommonResult.success(umsAdminMapper.selectByPrimaryKey(id));
	}
	
	@Operation(summary = "获取解密值", description = "获取解密值")
	@RequestMapping(value = "/key", method = RequestMethod.GET)
	public CommonResult<String> password() {
		return CommonResult.success(password);
	}

}