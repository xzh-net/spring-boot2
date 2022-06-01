package net.xzh.activiti.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import net.xzh.activiti.common.config.AuthProperties;
import net.xzh.activiti.model.User;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

	@Autowired
	private AuthProperties authProperties;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		User user = (User) request.getSession().getAttribute("user");
		if (!authProperties.getNoAuthUrls().contains(url)) {// 需要拦截认证的页面
			if ((user == null) || (user.getUsername() == null)) {
				log.info("NoAuth-ToLogin");
				response.sendRedirect(request.getContextPath() + "/");// 未登录自动跳转界面
				return false;
			}
		}
		return true;
	}
}