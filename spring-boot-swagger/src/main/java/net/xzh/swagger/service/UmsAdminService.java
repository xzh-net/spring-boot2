package net.xzh.swagger.service;

import net.xzh.swagger.service.impl.AdminUserDetails;

/**
 * 后台用于管理Service
 * Created xzh 2020/10/15.
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取用户信息
     */
    AdminUserDetails getAdminByUsername(String username);

    /**
     * 用户名密码登录
     */
    String login(String username, String password);
}
