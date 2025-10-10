package net.xzh.hikaricp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.hikaricp.common.model.CommonResult;
import net.xzh.hikaricp.mapper.UmsAdminMapper;
import net.xzh.hikaricp.model.UmsAdmin;

/**
 * 用户管理
 * 
 * @author Administrator
 *
 */
@RestController
public class IndexController {
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

}