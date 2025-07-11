package net.xzh.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.hutool.core.util.StrUtil;
import net.xzh.config.AuthProperties;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

	@Autowired
	private AuthProperties authProperties;

	/**
	 * 在调用方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("=======进入Login拦截器=======");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		// 使用AntPathMatcher进行路径匹配
	    AntPathMatcher pathMatcher = new AntPathMatcher();
	    boolean isNoAuth = authProperties.getNoAuthUrls().stream()
	            .anyMatch(pattern -> pathMatcher.match(pattern, url));
		if (isNoAuth) {// 需要拦截认证的页面
			String loginName = (String) request.getSession().getAttribute("user");
			if (StrUtil.isBlank(loginName)) {
				response.sendRedirect(request.getContextPath() + "/"); // 未登录自动跳转界面
				return false;
			}
		}
		return true;
	}
}