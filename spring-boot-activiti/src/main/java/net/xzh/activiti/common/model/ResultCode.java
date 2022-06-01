package net.xzh.activiti.common.model;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(0, "操作成功"),
    FAILED(500, "操作失败"),
	A0111(700, "用户名已存在");
    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
		return code;
	}


	public String getMessage() {
        return message;
    }
}
