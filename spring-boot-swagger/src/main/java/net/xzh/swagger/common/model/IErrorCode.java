package net.xzh.swagger.common.model;

/**
 * 封装API的错误码
 * Created xzh 2019/4/19.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
