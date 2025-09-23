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
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
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
 * SpringSecurity的配置
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
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
				.authorizeRequests();
		// 不需要保护的资源路径允许访问
		for (String url : ignoreUrlsConfig.getUrls()) {
			registry.antMatchers(url).permitAll();
		}
		// 允许跨域请求的OPTIONS请求
		registry.antMatchers(HttpMethod.OPTIONS).permitAll();
		httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
				.disable().sessionManagement()// 基于token，所以不需要session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().anyRequest()// 除上面外的所有请求全部需要鉴权认证
				.authenticated();
		// 禁用缓存
		httpSecurity.headers().cacheControl();
		// 添加JWT filter
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		// 添加自定义未授权和未登录结果返回
		httpSecurity.exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthenticationEntryPoint);
		// 有动态权限配置时添加动态权限校验过滤器
		if (dynamicSecurityService != null) {
			registry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
		}
		return httpSecurity.build();
	}

}
