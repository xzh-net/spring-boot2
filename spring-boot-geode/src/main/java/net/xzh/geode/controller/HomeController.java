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

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Object insert(@RequestBody User user) {
		userRepository.save(user);
		return "add OK";
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public Object findAll() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Object findById(String id) {
		return userRepository.findById(id);
	}

	@RequestMapping(value = "/deleteById", method = RequestMethod.DELETE)
	public Object deleteById(String id) {
		userRepository.deleteById(id);
		return "delete OK";

	}
}