package net.xzh.security.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.xzh.security.security.component.DynamicSecurityFilter;
import net.xzh.security.security.component.JwtAuthenticationTokenFilter;
import net.xzh.security.security.component.RestAuthenticationEntryPoint;
import net.xzh.security.security.component.RestfulAccessDeniedHandler;
import net.xzh.security.security.service.DynamicSecurityService;

/**
 * SecurityConfig - 安全流程配置
 */

@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
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

	/**
	 * token认证过滤器
	 */
	@Autowired
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

	 /**
     * 身份验证实现
     */
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(daoAuthenticationProvider);
	}

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
        // 禁用缓存
        .headers(headers -> headers.cacheControl())
        // 自定义权限拦截器JWT过滤器
        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exception -> exception
        	// 自定义权限拒绝处理类
            .accessDeniedHandler(restfulAccessDeniedHandler)
            // 自定义认证拒绝处理类
            .authenticationEntryPoint(restAuthenticationEntryPoint)
        );
    
		// 有动态权限配置时添加动态权限校验过滤器
		if (dynamicSecurityService != null) {
			httpSecurity.addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
		}
		return httpSecurity.build();
	}

}
