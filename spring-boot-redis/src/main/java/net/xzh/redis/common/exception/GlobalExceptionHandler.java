package net.xzh.redis.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xzh.redis.common.model.CommonResult;

/**
 * 全局异常处理
 * 
 * @author Administrator
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@ExceptionHandler(value = ApiException.class)
	public CommonResult handle(ApiException e) {
		if (e.getErrorCode() != null) {
			return CommonResult.failed(e.getErrorCode());
		}
		return CommonResult.failed(e.getMessage());
	}
}
