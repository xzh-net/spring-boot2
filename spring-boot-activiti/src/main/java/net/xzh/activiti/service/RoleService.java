package net.xzh.activiti.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.xzh.activiti.mapper.RoleMapper;
import net.xzh.activiti.model.Role;

@Service
public class RoleService {


    @Resource
    private RoleMapper roleMapper;


    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

}