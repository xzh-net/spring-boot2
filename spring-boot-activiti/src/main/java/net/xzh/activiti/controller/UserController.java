package net.xzh.activiti.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import net.xzh.activiti.common.model.PageResultBean;
import net.xzh.activiti.common.model.ResultBean;
import net.xzh.activiti.common.validate.groups.Create;
import net.xzh.activiti.model.User;
import net.xzh.activiti.service.RoleService;
import net.xzh.activiti.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    
    @Resource
    private RoleService roleService;


    @GetMapping("/index")
    public String index() {
        return "user/user-list";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<User> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        User userQuery) {
        List<User> users = userService.selectAllWithDept(page, limit, userQuery);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }
    
    @GetMapping
    public String add(Model model) {
        model.addAttribute("roles", roleService.selectAll());
        return "user/user-add";
    }
    
    @PostMapping
    @ResponseBody
    public ResultBean<?> add(@Validated(Create.class) User user, @RequestParam(value = "role[]", required = false) Integer[] roleIds) {
    	userService.add(user, roleIds);
    	return ResultBean.success(null);
    }
    
    @GetMapping("/{userId}")
    public String edit(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("roleIds", userService.selectRoleIdsById(userId));
        model.addAttribute("user", userService.selectOne(userId));
        model.addAttribute("roles", roleService.selectAll());
        return "user/user-add";
    }

    @PutMapping
    @ResponseBody
    public ResultBean<?> edit(@Valid User user, @RequestParam(value = "role[]", required = false) Integer[] roleIds) {
        userService.update(user, roleIds);
        return ResultBean.success(null);
    }
    
    @PostMapping("/{userId}/disable")
    @ResponseBody
    public ResultBean<?> disable(@PathVariable("userId") Integer userId) {
		userService.disableUserByID(userId);
		return ResultBean.success(null);
    }

    @PostMapping("/{userId}/enable")
    @ResponseBody
    public ResultBean<?> enable(@PathVariable("userId") Integer userId) {
        userService.enableUserByID(userId);
        return ResultBean.success(null);
    }

    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResultBean<?> delete(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return ResultBean.success(null);
    }

    @GetMapping("/{userId}/reset")
    public String resetPassword(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/user-reset-pwd";
    }

    @PostMapping("/{userId}/reset")
    @ResponseBody
    public ResultBean<?> resetPassword(@PathVariable("userId") Integer userId, String password) {
        userService.updatePasswordByUserId(userId, password);
        return ResultBean.success(null);
    }
}