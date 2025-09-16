package net.xzh.redis.common.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.xzh.redis.common.model.CommonResult;

/**
 * 全局异常处理
 * 
 * @author Administrator
 *
 */
@RestControllerAdvice
public class GlobalException {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(value = ApiException.class)
	public CommonResult handle(ApiException e) {
		if (e.getErrorCode() != null) {
			return CommonResult.failed(e.getErrorCode());
		}
		return CommonResult.failed(e.getMessage());
	}
}
