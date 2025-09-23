package net.xzh.security.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.xzh.security.common.model.CommonResult;


/**
 * 全局异常处理器
 * 使用@RestControllerAdvice注解标记为全局异常处理类
 */
@RestControllerAdvice
public class GlobalException {


    /**
     * 默认构造方法
     */
    public GlobalException() {
    }

    /**
     * 处理业务异常
     * @param e 业务异常对象
     * @return 统一的返回结果对象
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BusinessException.class})
    public CommonResult<Object> handle(BusinessException e) {
    	if (e.getErrorCode() != null) {
			return CommonResult.failed(e.getErrorCode());
		}
        return CommonResult.failed(e.getMessage());
    }
}
