package net.xzh.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.xzh.activiti.model.Dept;

@Mapper
public interface DeptMapper {


    List<Dept> selectAllTree();


}