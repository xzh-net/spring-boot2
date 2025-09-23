package net.xzh.security.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.xzh.security.security.domain.LoginUser;
import net.xzh.security.service.UmsAdminService;

/**
 * 用户验证处理
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UmsAdminService umsAdminService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		LoginUser loginUser = umsAdminService.getLoginByUsername(username);
		if (loginUser != null) {
			return loginUser;
		}
		throw new UsernameNotFoundException("用户名或密码错误");
	}
	

}
