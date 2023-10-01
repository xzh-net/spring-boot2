package net.xzh.websocket.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 因为登录使用了SecurityConfiguration模拟用户，此类模拟获取用户token(用户名称),因此数据没有选择去session中获取
 * @author admin
 *
 */
@RestController
public class UserController {
	
    @RequestMapping("/currentuser")
    public String getUsername(Principal principal){
        return principal.getName();
    }
}

