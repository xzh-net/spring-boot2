package net.xzh.elk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.elk.monitor.PointUtil;

/**
 * 用户管理
 * @author xzh
 *
 */
@RestController
public class UserController {

    @GetMapping(value = "getUserName")
    public String getUserName() {
    	PointUtil.info("我是个大盗贼info,{}",123);
    	PointUtil.debug("我是个大盗贼debug,{}",456);
        return "admin";
    }
}
