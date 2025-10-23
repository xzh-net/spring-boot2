package net.xzh.sockjs.common.exception;


import net.xzh.sockjs.common.model.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 */
public class Asserts {
    public static void fail(String message) {
        throw new BusinessException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new BusinessException(errorCode);
    }
}
