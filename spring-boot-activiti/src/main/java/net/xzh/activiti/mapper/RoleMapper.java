package net.xzh.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.xzh.activiti.model.Role;

@Mapper
public interface RoleMapper {
    List<Role> selectAll();
}