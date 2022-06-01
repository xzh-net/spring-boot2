package net.xzh.activiti.common.model;

public class ResultBean<T>{

    private String msg;
    private int code;
    private T data;

    private ResultBean() {
    }
    

    private ResultBean(int code, String message, T data) {
    	this.code = code;
        this.msg = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> ResultBean<T> success(T data, String message) {
        return new ResultBean<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> ResultBean<T> failed(IErrorCode errorCode) {
        return new ResultBean<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> ResultBean<T> failed(String message) {
        return new ResultBean<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> ResultBean<T> failed() {
        return failed(ResultCode.FAILED);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
