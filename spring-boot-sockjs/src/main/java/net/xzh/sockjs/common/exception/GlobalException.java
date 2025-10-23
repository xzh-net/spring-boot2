package net.xzh.sockjs.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.xzh.sockjs.common.model.CommonResult;
import net.xzh.sockjs.common.model.ResultCode;


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
    
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BusinessException.class})
    public CommonResult<Object> handle(BusinessException e) {
    	if (e.getErrorCode() != null) {
			return CommonResult.failed(e.getErrorCode());
		}
        return CommonResult.failed(ResultCode.FAILED);
    }

    
}
