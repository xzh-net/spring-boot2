package net.xzh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.xzh.Interceptor.LoginHandlerInterceptor;
import net.xzh.i18n.MyLocaleResolver;

/**
 * MVC初始化
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Autowired
	private LoginHandlerInterceptor loginHandlerInterceptor;

	/**
	 * 注册拦截器
	 * 
	 * @return
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**").// 对来自/*这个链接来的请求进行拦截
				excludePathPatterns("/error", "/webjars/**", "/css/**", "/js/**", "/images/**", "/plugins/**"); // 添加不拦截路径
	}


	/**
	 * 配置项目启动加载的首页
	 * 
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}

}
