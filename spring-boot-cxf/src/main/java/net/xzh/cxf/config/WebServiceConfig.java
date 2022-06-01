package net.xzh.cxf.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.cxf.service.CommonService;

@Configuration
public class WebServiceConfig {

	@Autowired
	private Bus bus;

	@Autowired
	CommonService commonService;

	@Bean
	public ServletRegistrationBean<CXFServlet> servletRegistrationBean() {
		ServletRegistrationBean<CXFServlet> bean = new ServletRegistrationBean<CXFServlet>(new CXFServlet(),
				"/webservice/*");
		bean.setLoadOnStartup(0);
		return bean;
	}

	// 终端路径
	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, commonService);
		endpoint.getInInterceptors().add(new AuthInterceptor());// 添加校验拦截器
		endpoint.publish("/gateway");
		return endpoint;
	}
}
