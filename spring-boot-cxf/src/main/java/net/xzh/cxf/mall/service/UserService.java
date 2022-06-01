package net.xzh.cxf.mall.service;

import java.util.Map;

import net.xzh.cxf.common.domain.UserDto;
import net.xzh.cxf.common.model.CommonResult;


/**
 * 用户登录
 *
 */
public interface UserService {
	
	CommonResult<Map<String,String>> act100001(Map<String, String> map);

	CommonResult<UserDto> act200001(Map<String, String> map);

	CommonResult<Map<String,String>> act300001(Map<String, String> map);
}
