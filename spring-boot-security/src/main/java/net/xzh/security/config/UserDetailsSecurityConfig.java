package net.xzh.security.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.xzh.security.domain.AdminUserDetails;
import net.xzh.security.domain.UmsResource;
import net.xzh.security.service.DynamicSecurityService;
import net.xzh.security.service.UmsAdminService;

/**
 * security模块相关配置
 * 自定义配置，用于配置如何获取用户信息及动态权限
 */
@Configuration
public class UserDetailsSecurityConfig {
	@Autowired
	private UmsAdminService adminService;

	@Bean
	public UserDetailsService userDetailsService() {
		// 获取登录用户信息
		return username -> {
			AdminUserDetails admin = adminService.getAdminByUsername(username);
			if (admin != null) {
				return admin;
			}
			throw new UsernameNotFoundException("用户名或密码错误");
		};
	}

	@Bean
	public DynamicSecurityService dynamicSecurityService() {
		return new DynamicSecurityService() {
			@Override
			public Map<String, ConfigAttribute> loadDataSource() {
				Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
				List<UmsResource> resourceList = adminService.getResourceList();
				for (UmsResource resource : resourceList) {
					map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(
							resource.getId() + ":" + resource.getName()));
				}
				return map;
			}
		};
	}
}
