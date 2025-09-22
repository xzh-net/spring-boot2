package net.xzh.security.security.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import net.xzh.security.domain.UmsResource;
import net.xzh.security.security.service.DynamicSecurityService;
import net.xzh.security.service.UmsAdminService;

/**
 * 动态权限查询数据
 */
@Service
public class DynamicSecurityServiceImpl implements DynamicSecurityService {

	@Autowired
	private UmsAdminService adminService;
	
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
	
}