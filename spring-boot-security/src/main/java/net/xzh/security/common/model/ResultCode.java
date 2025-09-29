package net.xzh.security.common.model;

/**
 * 枚举了一些常用API操作码
 * Created 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
	
    A0000("A0000", "操作成功"),
    A0001("A0001", "操作失败"),
    A0002("A0002", "参数检验失败"),
    A0003("A0003", "暂未登录或token已经过期"),
    A0004("A0004", "没有权限"),
    A0005("A0005", "账号密码错误"),
	A0006("A0006", "token签名不合法"),
	A0007("A0007", "token已过期");
	
    private String code;
    private String message;
    
    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
