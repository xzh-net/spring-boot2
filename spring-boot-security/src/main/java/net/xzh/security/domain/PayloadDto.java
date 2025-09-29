package net.xzh.security.domain;


import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 封装凭证数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "凭证数据")
public class PayloadDto {
    @Schema(description = "主题")
    private String sub;
    @Schema(description = "签发时间")
    private Long iat;
    @Schema(description = "过期时间")
    private Long exp;
    @Schema(description = "JWT的ID")
    private String jti;
    @Schema(description = "用户名称")
    private String username;
    @Schema(description = "用户拥有的权限")
    private List<String> authorities;
}
