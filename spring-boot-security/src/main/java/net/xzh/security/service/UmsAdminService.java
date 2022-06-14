package net.xzh.security.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.xzh.security.domain.AdminUserDetails;
import net.xzh.security.domain.UmsResource;

/**
 * 后台用于管理Service
 */
public interface UmsAdminService {
	/**
     * 根据用户名获取用户信息
     */
    AdminUserDetails getAdminByUsername(String username);

    /**
     * 获取所以权限列表
     */
    List<UmsResource> getResourceList();

    /**
     * 用户名密码登录
     */
    String login(String username, String password);
}
