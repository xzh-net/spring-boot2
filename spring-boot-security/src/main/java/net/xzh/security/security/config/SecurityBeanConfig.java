package net.xzh.security.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.xzh.security.security.component.DynamicAccessDecisionManager;
import net.xzh.security.security.component.DynamicSecurityFilter;
import net.xzh.security.security.component.DynamicSecurityMetadataSource;
import net.xzh.security.security.component.JwtAuthenticationTokenFilter;
import net.xzh.security.security.component.RestAuthenticationEntryPoint;
import net.xzh.security.security.component.RestfulAccessDeniedHandler;
import net.xzh.security.security.util.JwtTokenUtil;
import net.xzh.security.security.util.PwdEncoderUtil;

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
		return PwdEncoderUtil.getDelegatingPasswordEncoder("SM3");
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
