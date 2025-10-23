package net.xzh.sockjs.security.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import cn.hutool.json.JSONUtil;
import net.xzh.sockjs.common.model.CommonResult;
import net.xzh.sockjs.common.model.ResultCode;

/**
 * 处理授权失败
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().println(JSONUtil.parse(CommonResult.failed(ResultCode.FORBIDDEN)));
		response.getWriter().flush();
	}
}
