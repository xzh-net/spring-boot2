package net.xzh.sockjs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.sockjs.common.model.CommonResult;
import net.xzh.sockjs.service.UmsAdminService;

/**
 * 后台用户管理
 */
@RestController
public class UmsAdminController {
	
	@Autowired
    private UmsAdminService umsAdminService;
	
	// 令牌前缀
    @Value("${token.prefix}")
    private String prefix;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<?> login(@RequestParam String username, @RequestParam String password) {
        String token = umsAdminService.login(username, password);
        return CommonResult.success(prefix+token);
    }
    
}
