package net.xzh.project.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.xzh.project.security.component.DynamicSecurityFilter;
import net.xzh.project.security.component.DynamicSecurityService;
import net.xzh.project.security.component.JwtAuthenticationTokenFilter;
import net.xzh.project.security.component.RestAuthenticationEntryPoint;
import net.xzh.project.security.component.RestfulAccessDeniedHandler;

/**
 * SpringSecurity的配置
 */
@Configuration
public class SecurityConfig {

	/**
	 * 忽略认证地址
	 */
	@Autowired
	private IgnoreUrlsConfig ignoreUrlsConfig;

	/**
	 * 处理授权失败
	 */
	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

	/**
	 * 处理认证失败
	 */
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Autowired

	/**
	 * token认证过滤器
	 */
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	/**
	 * 动态权限扩展接口
	 */
	@Autowired
	private DynamicSecurityService dynamicSecurityService;

	/**
	 * 动态权限过滤器
	 */
	@Autowired
	private DynamicSecurityFilter dynamicSecurityFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    HttpSecurity httpSecurity = http
	        .authorizeHttpRequests(authz -> authz
	        	.antMatchers(HttpMethod.OPTIONS).permitAll()
	            .antMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0])).permitAll()
	            .anyRequest().authenticated()
	        )
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .exceptionHandling(exception -> exception
	        	// 自定义权限拒绝处理类
	            .accessDeniedHandler(restfulAccessDeniedHandler)
	            // 自定义认证拒绝处理类
	            .authenticationEntryPoint(restAuthenticationEntryPoint)
	        )
	        // 自定义权限拦截器JWT过滤器
	        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	    
	    // 有动态权限配置时添加动态权限校验过滤器
	    if (dynamicSecurityService != null) {
	        httpSecurity.addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
	    }
	    return httpSecurity.build();
	}
}
