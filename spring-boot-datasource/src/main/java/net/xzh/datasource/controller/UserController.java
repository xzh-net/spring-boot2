package net.xzh.datasource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.datasource.model.User;
import net.xzh.datasource.service.UserService;

/**
 * 用户管理（手动管理数据源）
 * @author CR7
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 默认数据源
     * @return
     */
    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }
    
    /**
     * 切换数据源
     * @param datasourceKey
     * @return
     */
    @GetMapping("/list/{datasourceKey}")
    public List<User> listByDs(@PathVariable String datasourceKey) {
        return userService.list(datasourceKey);
    }
}