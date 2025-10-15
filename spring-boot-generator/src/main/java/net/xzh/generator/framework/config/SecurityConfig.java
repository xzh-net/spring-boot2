package net.xzh.generator.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security拦截配置
 * 
 * @author xzh
 *
 */
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.authorizeRequests(authz -> authz
				// 允许跨域请求的OPTIONS请求
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers(
						"/generator/**",
						"/sysuser/**",
						"/swagger-ui/**",
						"/api-docs/**").permitAll()
				// 任何请求需要身份认证
				.anyRequest().authenticated())
				// 关闭跨站请求防护及不使用session
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
	}
}