package net.xzh.activiti.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.xzh.activiti.mapper.DeptMapper;
import net.xzh.activiti.model.Dept;

@Service
public class DeptService {

    @Resource
    private DeptMapper deptMapper;

    /**
     * 查找所有的部门的树形结构
     */
    public List<Dept> selectAllDeptTree() {
        return deptMapper.selectAllTree();
    }

}
