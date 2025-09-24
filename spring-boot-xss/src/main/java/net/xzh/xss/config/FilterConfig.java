package net.xzh.xss.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.xss.filter.RefererFilter;
import net.xzh.xss.filter.XssFilter;

/**
 * Filter配置，注册XSS过滤器和Referer过滤器
 * 
 */
@Configuration
public class FilterConfig {
	
	// 从配置文件中获取需要排除的URL模式
	@Value("${xss.excludes}")
	private String excludes;

	// 从配置文件中获取需要应用XSS过滤的URL模式
	@Value("${xss.urlPatterns}")
	private String urlPatterns;
	
	// 从配置文件中获取允许的Referer域名
	@Value("${referer.allowed-domains}")
    private String allowedDomains;

	/**
     * 注册XSS过滤器
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
	public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
		FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<XssFilter>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter());
		registration.addUrlPatterns(urlPatterns.split(","));
		registration.setName("xssFilter");
		registration.setOrder(Integer.MAX_VALUE);
		Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("excludes", excludes);
		registration.setInitParameters(initParameters);
		return registration;
	}
	
	/**
     * 注册Referer过滤器
	 * @return
	 */
	@Bean
    @ConditionalOnProperty(value = "referer.enabled", havingValue = "true")
    public FilterRegistrationBean<RefererFilter> refererFilterRegistration()
    {
        FilterRegistrationBean<RefererFilter> registration = new FilterRegistrationBean<RefererFilter>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new RefererFilter());
        registration.addUrlPatterns("/*");
        registration.setName("refererFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("allowedDomains", allowedDomains);
        registration.setInitParameters(initParameters);
        return registration;
    }
}