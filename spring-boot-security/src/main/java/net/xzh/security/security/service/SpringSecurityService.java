package net.xzh.security.security.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import net.xzh.security.security.context.PermissionContextHolder;
import net.xzh.security.security.domain.LoginUser;
import net.xzh.security.security.util.SecurityUtils;

/**
 * 自定义权限实现
 * 
 */
@Service("sss")
public class SpringSecurityService {
	
	/**
     * 所有权限标识,例 brand:list
     */
    public static final String ALL_PERMISSION = "*:*:*";
	
	/**
     * 验证用户是否具备某权限
     * 
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission)
    {
        if (StrUtil.isEmpty(permission))
        {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtil.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        PermissionContextHolder.setContext(permission);
        return hasPermissions(loginUser.getPermissions(), permission);
    }
    
	/**
     * 判断是否包含权限
     * 
     * @param permissions 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
	private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StrUtil.trim(permission));
    }
}
