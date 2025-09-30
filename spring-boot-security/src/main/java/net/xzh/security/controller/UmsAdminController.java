package net.xzh.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.security.common.model.CommonResult;
import net.xzh.security.model.UmsAdmin;
import net.xzh.security.service.UmsAdminService;

/**
 * 用户管理
 */
@Tag(name = "用户管理", description = "用户管理相关的API")
@RestController
public class UmsAdminController {
	
	
	// 令牌前缀
    @Value("${token.prefix}")
    private String prefix;
    
    @Autowired
    private UmsAdminService umsAdminService;
    
    @Operation(summary = "用户登录", description = "账号密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<?> login(@RequestParam String username, @RequestParam String password) {
        String token = umsAdminService.login2(username, password);
        return CommonResult.success(prefix+token);
    }
    
    
    @Operation(summary = "用户注册", description = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam) {
        umsAdminService.register(umsAdminParam);
        return CommonResult.success(null);
    }
    
    @PreAuthorize("@sss.hasPermi('6:admin:resetPwd')")
    @Operation(summary = "重置密码", description = "按用户id重置密码")
    @RequestMapping(value = "/resetPwd/{id}", method = RequestMethod.PUT)
    public CommonResult<?> resetPwd(@PathVariable Long id) {
        return CommonResult.success(null);
    }
}
