package net.xzh.sockjs.security.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import net.xzh.sockjs.entity.UmsResource;
import net.xzh.sockjs.security.service.DynamicSecurityService;
import net.xzh.sockjs.service.UmsAdminService;
import net.xzh.sockjs.service.UmsResourceService;

/**
 * 动态权限查询数据
 */
@Service
public class DynamicSecurityServiceImpl implements DynamicSecurityService {

	@Autowired
    private UmsResourceService resourceService;
	
	@Autowired
    private UmsAdminService adminService;
	
	@Override
	public Map<String, ConfigAttribute> loadDataSource() {
		Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<UmsResource> resourceList = resourceService.listAll();
        for (UmsResource resource : resourceList) {
            map.put(resource.getUrl(), new SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
        return map;
	}
	
	@Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }
}