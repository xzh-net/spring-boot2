package net.xzh.security.common.exception;

import net.xzh.security.common.model.IErrorCode;

/**
 * 自定义业务异常类，继承自RuntimeException 用于在业务逻辑出现错误时抛出异常
 */

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 6857947893524608598L;
	private IErrorCode errorCode;

	public BusinessException(IErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}


	public IErrorCode getErrorCode() {
		return this.errorCode;
	}
}
