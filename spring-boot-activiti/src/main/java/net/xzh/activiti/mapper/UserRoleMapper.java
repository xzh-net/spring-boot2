package net.xzh.activiti.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {


    /**
     * 插入多条 用户色-角色 关联关系
     */
    int insertList(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    /**
     * 清空用户所拥有的所有角色
     */
    int deleteUserRoleByUserId(@Param("userId") Integer userId);


}