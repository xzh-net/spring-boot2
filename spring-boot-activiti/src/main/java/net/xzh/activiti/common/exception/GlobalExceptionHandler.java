package net.xzh.activiti.common.exception;


import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xzh.activiti.common.model.ResultBean;


/**
 * 全局异常处理
 * @author CR7
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @SuppressWarnings("rawtypes")
	@ResponseBody
	@ExceptionHandler(value = ApiException.class)
	public ResultBean ApiExceptionhandle(ApiException e) {
    	if (e.getErrorCode() != null) {
            return ResultBean.failed(e.getErrorCode());
        }
        return ResultBean.failed(e.getMessage());
	}
    
    @ResponseBody
	@ExceptionHandler(value = Exception.class)
    public ResultBean<?> all(Exception e) {
    	return ResultBean.failed(e.getMessage());
    }
    
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class, ClientAbortException.class})
    @ResponseBody
    public void clientAbortException(Exception ex) {
        if (log.isDebugEnabled()) {
            log.debug("出现了断开异常:", ex);
        }
    }


}
