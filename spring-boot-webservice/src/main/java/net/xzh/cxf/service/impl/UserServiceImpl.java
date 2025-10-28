package net.xzh.cxf.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import net.xzh.cxf.core.annotation.SoapMapping;
import net.xzh.cxf.core.domain.UserDto;
import net.xzh.cxf.core.model.CommonResult;
import net.xzh.cxf.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	@SoapMapping(funcId = "100001")
	public CommonResult<Map<String,String>> act100001(Map<String, String> map) {
		map.put("flag", "delete");
		map.put("timestamp", System.currentTimeMillis()+"");
		return CommonResult.success(map);
	}
	

	/**
	 * 模拟selectOne
	 */
	@SoapMapping(funcId = "200001")
	public CommonResult<UserDto> act200001(Map<String, String> map) {
		UserDto userDto = new UserDto();
		userDto.setUsername("李四");
		userDto.setPassword("123456");
		userDto.setStatus(1);
		return CommonResult.success(userDto);
	}

	

	@Override
	public CommonResult<Map<String, String>> act300001(Map<String, String> map) {
		map.put("times", System.currentTimeMillis() + "");
		return CommonResult.success(map);
	}

}
