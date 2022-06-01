package net.xzh.redis.common.exception;

import net.xzh.redis.common.model.IErrorCode;

/**
 * 自定义API异常
 * 
 * @author Administrator
 *
 */
public class ApiException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2473015798030359736L;
	private IErrorCode errorCode;

	public ApiException(IErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public IErrorCode getErrorCode() {
		return errorCode;
	}
}
