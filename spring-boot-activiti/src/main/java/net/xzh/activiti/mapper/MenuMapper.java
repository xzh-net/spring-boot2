package net.xzh.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.xzh.activiti.model.Menu;

@Mapper
public interface MenuMapper {
  
    /**
     * 获取所有菜单
     */
    List<Menu> selectAll();

}