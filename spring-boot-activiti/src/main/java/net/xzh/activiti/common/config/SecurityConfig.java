package net.xzh.activiti.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.xzh.activiti.common.component.JwtAuthenticationTokenFilter;
import net.xzh.activiti.common.config.properties.IgnoreUrlsProperties;

/**
 * 安全设置
 * 
 * @author xzh
 *
 */
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	@Autowired
	private IgnoreUrlsProperties ignoreUrlsProperties;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz
				// 允许跨域请求的OPTIONS请求
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers(ignoreUrlsProperties.getUrls().toArray(new String[0])).permitAll().anyRequest()
				.authenticated()).formLogin(form -> form.loginPage("/index") // 自定义登录页面
						.permitAll())
				.logout(logout -> logout.permitAll()).csrf().disable()
				.headers(headers -> headers.frameOptions().sameOrigin() // 允许同源 iframe
				).addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}