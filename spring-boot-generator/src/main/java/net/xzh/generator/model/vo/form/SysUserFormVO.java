package net.xzh.generator.model.vo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * 用户管理提交对象
 *
 * @author xzh
 * @date 2025-10-14 20:48:36
 */
@Data
@Schema(description = "用户管理提交对象")
public class SysUserFormVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
            @NotEmpty(message = "账号不能为空")
                @Schema(description = "账号")
    private String username;
    
    /**
     * 密码
     */
            @NotEmpty(message = "密码不能为空")
                @Schema(description = "密码")
    private String password;
    
    /**
     * 昵称
     */
            @NotEmpty(message = "昵称不能为空")
                @Schema(description = "昵称")
    private String nickname;
    
    /**
     * 头像
     */
            @Schema(description = "头像")
    private String headimgurl;
    
    /**
     * 电话
     */
            @Schema(description = "电话")
    private String mobile;
    
    /**
     * 邮箱
     */
            @Schema(description = "邮箱")
    private String email;
    
    /**
     * 余额
     */
            @Schema(description = "余额")
    private BigDecimal accountBalance;
    
    /**
     * 状态（0正常 1禁用 ）
     */
            @NotNull(message = "状态（0正常 1禁用 ）不能为空")
                @Schema(description = "状态（0正常 1禁用 ）")
    private Integer status;
    
    /**
     * 注册日期
     */
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
        @Schema(description = "注册日期")
    private Date registDate;
    
    /**
     * 排序
     */
            @Schema(description = "排序")
    private Integer sortBy;
    
}