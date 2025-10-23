package net.xzh.sockjs.security.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相业
 */

public interface DynamicSecurityService {
	/**
	 * 加载资源ANT通配符和资源对应MAP
	 */
	Map<String, ConfigAttribute> loadDataSource();
}
