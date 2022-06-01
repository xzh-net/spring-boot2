package net.xzh.cxf.mall.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import net.xzh.cxf.common.annotation.SoapMapping;
import net.xzh.cxf.common.domain.UserDto;
import net.xzh.cxf.common.model.CommonResult;
import net.xzh.cxf.mall.service.UserService;

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
		userDto.setUsername("admin");
		userDto.setPassword("123456");
		return CommonResult.success(userDto);
	}

	

	@Override
	public CommonResult<Map<String, String>> act300001(Map<String, String> map) {
		map.put("times", System.currentTimeMillis() + "");
		return CommonResult.success(map);
	}

}
