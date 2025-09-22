package net.xzh.security.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.xzh.security.domain.AdminUserDetails;
import net.xzh.security.service.UmsAdminService;

/**
 * 用户验证处理
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UmsAdminService adminService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		AdminUserDetails admin = adminService.getAdminByUsername(username);
		if (admin != null) {
			return admin;
		}
		throw new UsernameNotFoundException("用户名或密码错误");
	}
	

}
