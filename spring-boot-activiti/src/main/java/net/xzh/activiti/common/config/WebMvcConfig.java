package net.xzh.activiti.common.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.xzh.activiti.common.interceptor.LoginHandlerInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Resource
	private LoginHandlerInterceptor loginHandlerInterceptor;

	/**
	 * 添加拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginHandlerInterceptor).excludePathPatterns(
				Arrays.asList("/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**", "/error"));
	}
	
	/**
	 * 配置项目启动加载的首页
	 * 
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

}