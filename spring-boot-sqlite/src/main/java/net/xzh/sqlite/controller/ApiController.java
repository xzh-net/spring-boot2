package net.xzh.sqlite.controller;


import java.time.LocalDateTime;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.sqlite.model.User;
import net.xzh.sqlite.service.UserService;

@RestController
public class ApiController {

    @Autowired
    private UserService userService;

    @GetMapping("/init")
    public String init(){
    	for (int i = 0; i < 10; i++) {
            User user = new User();
            // 随机4个字母
            user.setName(RandomStringUtils.randomAlphabetic(4));
            // 随机16个字符用于密码加盐加密
            user.setSalt(RandomStringUtils.randomAlphanumeric(16));
            String password = "123456";
            // 密码存储 = md5(密码+盐)
            password = password + user.getSalt();
            user.setPassword(DigestUtils.md5Hex(password));
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setStatus("active");
            userService.save(user);
        }
        return "初始化完成。";
    }
    
    @GetMapping("/login")
    public String findByUsername(String username, String password) {
        User user = userService.getByName(username);
        if (user == null) {
            return "用户不存在";
        }
        password = password + user.getSalt();
        if (StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())) {
            return "登录成功";
        } else {
            return "密码错误";
        }
    }

    @GetMapping("/userByName/{username}")
    public User getUserByName(@PathVariable("username") String username){
        return userService.getByName(username);
    }

    @GetMapping("/userById/{userid}")
    public User getUserById(@PathVariable("userid") Long userid){
        return userService.getUserByID(userid);
    }

    @GetMapping("/page")
    public Page<User> getPage(){
        return userService.findPage();
    }

    @GetMapping("/page/{maxID}")
    public Page<User> getPageByMaxID(@PathVariable("maxID") Long maxID){
        return userService.find(maxID);
    }

    @RequestMapping("/update/{id}/{name}")
    public User update(@PathVariable Long id, @PathVariable String name){
        return userService.update(id,name);
    }

    @RequestMapping("/update/{id}")
    public Boolean updateById(@PathVariable Long id){
        return userService.updateById("newName",id);
    }
}