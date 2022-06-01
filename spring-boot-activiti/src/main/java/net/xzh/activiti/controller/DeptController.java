package net.xzh.activiti.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xzh.activiti.common.model.ResultBean;
import net.xzh.activiti.service.DeptService;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    @GetMapping("/tree")
    @ResponseBody
    public ResultBean<?> tree() {
        return ResultBean.success(deptService.selectAllDeptTree());
    }

}
