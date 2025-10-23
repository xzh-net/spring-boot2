package net.xzh.sockjs.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.xzh.sockjs.security.component.DynamicAccessDecisionManager;
import net.xzh.sockjs.security.component.DynamicSecurityFilter;
import net.xzh.sockjs.security.component.DynamicSecurityMetadataSource;
import net.xzh.sockjs.security.component.JwtAuthenticationTokenFilter;
import net.xzh.sockjs.security.component.RestAuthenticationEntryPoint;
import net.xzh.sockjs.security.component.RestfulAccessDeniedHandler;
import net.xzh.sockjs.security.util.JwtTokenUtil;

/**
 * 无状态的基础组件，不依赖其他安全组件
 */
@Configuration
public class SecurityBeanConfig {

	@Bean
	public JwtTokenUtil jwtTokenUtil() {
		return new JwtTokenUtil();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
		return new RestfulAccessDeniedHandler();
	}

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
		return new DynamicAccessDecisionManager();
	}

	@Bean
	public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
		return new DynamicSecurityMetadataSource();
	}

	@Bean
	public DynamicSecurityFilter dynamicSecurityFilter() {
		return new DynamicSecurityFilter();
	}
}
