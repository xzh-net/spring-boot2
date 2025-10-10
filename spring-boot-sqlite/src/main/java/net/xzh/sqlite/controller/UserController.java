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

/**
 * 用户管理
 * @author CR7
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 初始化数据
	 * 
	 * @return
	 */
	@GetMapping("/init")
	public String init() {
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
		return "初始化成功";
	}

	/**
	 * 用户密码登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@GetMapping("/login")
	public String login(String username, String password) {
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

	/**
	 * 按用户名查找
	 * 
	 * @param username
	 * @return
	 */
	@GetMapping("/userByName/{username}")
	public User getUserByName(@PathVariable("username") String username) {
		return userService.getByName(username);
	}

	/**
	 * 按照用户id查找
	 * 
	 * @param userid
	 * @return
	 */
	@GetMapping("/userById/{userid}")
	public User getUserById(@PathVariable("userid") Long userid) {
		return userService.getUserByID(userid);
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@GetMapping("/page")
	public Page<User> page() {
		return userService.findPage();
	}

	/**
	 * 查询所有ID ≤ maxId 用户，分页查询
	 * 
	 * @return
	 */
	@GetMapping("/page/{maxId}")
	public Page<User> getPageByMaxID(@PathVariable("maxId") Long maxId) {
		return userService.find(maxId);
	}

	/**
	 * 更新指定用户的名称
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping("/update/{id}/{name}")
	public User update(@PathVariable Long id, @PathVariable String name) {
		return userService.update(id, name);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/resetPwd/{id}")
	public Boolean resetPwd(@PathVariable Long id) {
		// 密码存储 = md5(密码+盐)
		String salt= RandomStringUtils.randomAlphanumeric(16);
		String password = "000000"+salt;
		password = DigestUtils.md5Hex(password);
		return userService.resetPwd(password,salt, id);
	}
}