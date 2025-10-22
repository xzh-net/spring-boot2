package net.xzh.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.webflux.model.User;
import net.xzh.webflux.service.UserSerivice;
import reactor.core.publisher.Mono;

/**
 * 用户管理
 * 
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserSerivice userSerivice;

	@GetMapping("{id}")
	public Mono<User> get(@PathVariable Long id) {
		return userSerivice.getUserById(id);
	}
}
