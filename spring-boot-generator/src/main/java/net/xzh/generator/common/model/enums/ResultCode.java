package net.xzh.generator.common.model.enums;

/**
 * 枚举了一些常用API操作码
 * @author xzh
 *
 */
public enum ResultCode {
    SUCCESS(200),
    ERROR(500);

    private Integer code;
    
    ResultCode(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
