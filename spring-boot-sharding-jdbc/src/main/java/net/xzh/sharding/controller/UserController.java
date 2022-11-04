package net.xzh.sharding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.sharding.common.model.CommonResult;
import net.xzh.sharding.model.User;
import net.xzh.sharding.service.IUserService;

/**
 * @author xzh
 */
@Api(tags = "用户管理")
@RequestMapping("/user")
@RestController
public class UserController {
	
    @Autowired
	private IUserService userService;

    /**
     * 初始化数据
     */
    @ApiOperation("数据初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public CommonResult initDate() {
        String companyId;
        for (int i = 0; i < 100; i++) {
            User u = new User();
            if (i % 2 == 0) {
                companyId = "alibaba";
            } else {
                companyId = "baidu";
            }
            u.setCompanyId(companyId);
            u.setName(String.valueOf(i));
            userService.save(u);
        }
        return CommonResult.success("success");
    }

    /**
     * 查询列表
     */
    @ApiOperation("查询列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<User>> list() {
        return CommonResult.success(userService.list(new QueryWrapper<User>().orderByAsc("id")));
    }
    
    
    /**
     * 查询单条记录
     */
    @ApiOperation("获取指定id的用户详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<User> user(@PathVariable("id") Long id) {
        return CommonResult.success(userService.getById(id));
    }
    
    /**
     * 清除数据
     */
    @ApiOperation("清除数据")
    @RequestMapping(value = "/clean", method = RequestMethod.GET)
    public CommonResult clean() {
        userService.remove(null);
        return CommonResult.success("success");
    }
}
