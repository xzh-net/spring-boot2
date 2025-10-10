package net.xzh.jasypt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.jasypt.common.model.CommonResult;
import net.xzh.jasypt.mapper.UmsAdminMapper;
import net.xzh.jasypt.model.UmsAdmin;

/**
 * 用户管理
 * 
 */
@RestController
public class UserController {
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Autowired
	private UmsAdminMapper umsAdminMapper;

	/**
	 * 获取指定id的用户详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public CommonResult<UmsAdmin> user(@PathVariable(value = "id") Long id) {
		return CommonResult.success(umsAdminMapper.selectByPrimaryKey(id));
	}
	
	/**
	 * 获取解密值
	 * @return
	 */
	@RequestMapping(value = "/key", method = RequestMethod.GET)
	public CommonResult<String> password() {
		return CommonResult.success(password);
	}

}