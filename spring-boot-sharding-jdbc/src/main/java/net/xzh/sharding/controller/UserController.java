package net.xzh.sharding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import net.xzh.sharding.model.User;
import net.xzh.sharding.service.IUserService;

/**
 * 用户管理
 * @author xzh
 */
@RequestMapping("/user")
@RestController
public class UserController {
	
    @Autowired
	private IUserService userService;

    /**
     * 初始化数据
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
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
        return "success";
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> list() {
        return userService.list(new QueryWrapper<User>().orderByAsc("id"));
    }
    
    
    /**
     * 获取用户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable("id") Long id) {
        return userService.getById(id);
    }
    
    /**
     * 清除数据
     * @return
     */
    @RequestMapping(value = "/clean", method = RequestMethod.GET)
    public String clean() {
        userService.remove(null);
        return "success";
    }
}
