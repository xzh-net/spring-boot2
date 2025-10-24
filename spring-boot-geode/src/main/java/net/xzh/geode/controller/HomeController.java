package net.xzh.geode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.geode.entity.User;
import net.xzh.geode.repository.UserRepository;

/**
 * 测试数据插入和查询
 * 
 * @author CR7
 *
 */
@RestController
public class HomeController {

	@Autowired
	UserRepository userRepository;

	/**
	 * 插入用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(@RequestBody User user) {
		userRepository.save(user);
		return "创建成功";
	}

	/**
	 * 查询全部
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object list() {
		return userRepository.findAll();
	}
	/**
	 * 按用户id查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Object findById(String id) {
		return userRepository.findById(id);
	}

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removeById", method = RequestMethod.DELETE)
	public Object removeById(String id) {
		userRepository.deleteById(id);
		return "删除成功";

	}
}