package net.xzh.cxf.client.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.json.JSONUtil;
import net.xzh.cxf.client.config.AuthHeaderInterceptor;
import net.xzh.cxf.core.service.CommonService;

/**
 * cxf客户端
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/client")
public class UserController {
	
	@Value("${cxf.remoteurl}")
	String remoteurl;
	
	/**
	 * 方式1:使用代理类工厂,需要拿到对方的接口
	 */
	@RequestMapping(value = "/proxyFactory", method = RequestMethod.GET)
	public String test2() {
		// 代理工厂
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		// 设置代理地址
		jaxWsProxyFactoryBean.setAddress(remoteurl);
		// 添加用户名密码拦截器
		jaxWsProxyFactoryBean.getOutInterceptors().add(new AuthHeaderInterceptor("admin", "123456")); 
		// 添加出入参日志
		jaxWsProxyFactoryBean.getInInterceptors().add(new LoggingInInterceptor());
		jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
		// 设置接口类型
		jaxWsProxyFactoryBean.setServiceClass(CommonService.class);
		// 创建一个代理接口实现
		CommonService cs = (CommonService) jaxWsProxyFactoryBean.create();
		// 数据准备
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "我们都是好孩子");
		map.put("memo", "我是个大盗贼");
		return cs.doServices("100001", JSONUtil.toJsonStr(map));
	}

	/**
	 * 动态调用
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/dynamic", method = RequestMethod.GET)
	public String DynamicClient() throws Exception {
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(remoteurl);
		// 添加出入参日志
		client.getInInterceptors().add(new LoggingInInterceptor());
		client.getOutInterceptors().add(new LoggingOutInterceptor());
		// 需要密码的情况需要加上用户名和密码
		client.getOutInterceptors().add(new AuthHeaderInterceptor("admin", "123456"));
		String[] params = { "200001", "{\"name\":\"xzh\",\"memo\":\"我们都是好孩子\"}" };
		return (String) client.invoke("doServices", params)[0];

	}
}