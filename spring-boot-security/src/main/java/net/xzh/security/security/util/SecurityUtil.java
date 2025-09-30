package net.xzh.security.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.xzh.security.common.exception.BusinessException;
import net.xzh.security.common.model.ResultCode;
import net.xzh.security.security.domain.LoginUser;

/**
 * 安全服务工具类
 * 
 */
public class SecurityUtil {
	
	
	public static LoginUser getLoginUser() {
		try {
			return (LoginUser) getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new BusinessException(ResultCode.A0003);
		}
	}
	
	 /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
