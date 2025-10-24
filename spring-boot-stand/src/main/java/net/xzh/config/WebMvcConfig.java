package net.xzh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.xzh.Interceptor.LoginHandlerInterceptor;
import net.xzh.config.properties.IgnoreUrlsProperties;
import net.xzh.i18n.MyLocaleResolver;

/**
 * WebMvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	private LoginHandlerInterceptor loginHandlerInterceptor;

	private IgnoreUrlsProperties ignoreUrlsProperties;

	// 使用构造器注入替代字段注入
	public WebMvcConfig(LoginHandlerInterceptor loginHandlerInterceptor, IgnoreUrlsProperties ignoreUrlsProperties) {
		this.loginHandlerInterceptor = loginHandlerInterceptor;
		this.ignoreUrlsProperties = ignoreUrlsProperties;
	}
	/**
	 * 注册拦截器
	 * 
	 * @return
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**").// 对来自/**这个链接来的请求进行拦截
				excludePathPatterns(ignoreUrlsProperties.getUrls()); // 添加不拦截路径
	}

	/**
	 * 配置视图控制器
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	
	/**
	 * 注册自定义区域解析器
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}

}
