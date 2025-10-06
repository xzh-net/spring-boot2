package net.xzh.datasource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.datasource.model.Role;
import net.xzh.datasource.service.RoleService;

/**
 * 角色管理（多数据源使用注解切换）
 * @author CR7
 *
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 主数据源
     * @return
     */
    @GetMapping("/list")
    public List<Role> list() {
        return roleService.list();
    }
    
    /**
     * 从数据源
     * @return
     */
    @GetMapping("/list_slave")
    public List<Role> list_slave() {
        return roleService.list_slave();
    }
}