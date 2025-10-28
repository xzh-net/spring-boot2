package net.xzh.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import net.xzh.dubbo.service.UserService;

/**
 * 
 * @author Administrator
 *
 */
@Component
@DubboService
public class UserServiceImpl implements UserService {

	@Override
	public String getUserName(String userName) {
		return "你好："+ userName;
	}
}
