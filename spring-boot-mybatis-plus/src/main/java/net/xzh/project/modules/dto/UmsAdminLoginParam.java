package net.xzh.project.modules.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户登录参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "登录用户")
public class UmsAdminLoginParam {
    @NotEmpty
    @Schema(description = "用户名")
    private String username;
    @NotEmpty
    @Schema(description = "密码")
    private String password;
}
