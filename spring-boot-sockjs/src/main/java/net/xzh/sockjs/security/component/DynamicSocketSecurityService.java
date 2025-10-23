package net.xzh.sockjs.security.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import cn.hutool.core.collection.CollUtil;
import net.xzh.sockjs.security.service.DynamicSecurityService;

/**
 * socket订阅，动态权限数据源
 */
@Component
public class DynamicSocketSecurityService {

	@Autowired
	private DynamicSecurityService dynamicSecurityService;

	private Map<String, ConfigAttribute> configAttributeMap;
	private PathMatcher pathMatcher = new AntPathMatcher();

	@PostConstruct
	public void loadDataSource() {
		configAttributeMap = dynamicSecurityService.loadDataSource();
	}

	public boolean checkTopicPermission(Authentication authentication, String topic) {
		if (configAttributeMap == null) {
			loadDataSource();
		}

		Collection<ConfigAttribute> configAttributes = getAttributes(topic);
		if (CollUtil.isEmpty(configAttributes)) {
			return false;
		}

		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			String needAuthority = configAttribute.getAttribute();
			for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
				if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	private Collection<ConfigAttribute> getAttributes(String topic) {
		List<ConfigAttribute> configAttributes = new ArrayList<>();
		Iterator<String> iterator = configAttributeMap.keySet().iterator();

		while (iterator.hasNext()) {
			String pattern = iterator.next();
			if (pathMatcher.match(pattern, topic)) {
				configAttributes.add(configAttributeMap.get(pattern));
			}
		}
		return configAttributes;
	}

	public void clearDataSource() {
		configAttributeMap.clear();
		configAttributeMap = null;
	}
}
