package net.xzh.harbor.common.model;

/**
 * 封装API的错误码
 * Created 2019/4/19.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
