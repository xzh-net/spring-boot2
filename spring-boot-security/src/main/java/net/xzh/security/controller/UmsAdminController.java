package net.xzh.security.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.security.common.model.CommonResult;
import net.xzh.security.model.UmsAdmin;
import net.xzh.security.service.UmsAdminService;

/**
 * 用户管理
 */
@Api(tags = "用户管理")
@RequestMapping("/admin")
@RestController
public class UmsAdminController {
	
    @Autowired
    private UmsAdminService umsAdminService;
    
    // 令牌前缀
    @Value("${token.prefix}")
    private String prefix;

    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<?> login(@RequestParam String username, @RequestParam String password) {
        String token = umsAdminService.login(username, password);
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("prefix", prefix);
        return CommonResult.success(tokenMap);
    }
    
    
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }
    
    @PreAuthorize("@sss.hasPermi('6:admin:resetPwd')")
    @ApiOperation(value = "重置密码")
    @RequestMapping(value = "/resetPwd/{id}", method = RequestMethod.PUT)
    public CommonResult<?> resetPwd(@PathVariable Long id) {
        return CommonResult.success("重置成功");
    }
}
