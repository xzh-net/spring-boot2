package net.xzh.cxf.service;

import java.util.Map;

import net.xzh.cxf.core.domain.UserDto;
import net.xzh.cxf.core.model.CommonResult;


/**
 * 用户登录
 *
 */
public interface UserService {
	
	CommonResult<Map<String,String>> act100001(Map<String, String> map);

	CommonResult<UserDto> act200001(Map<String, String> map);

	CommonResult<Map<String,String>> act300001(Map<String, String> map);
}
