package net.xzh.webflux.service;

import net.xzh.webflux.model.User;
import reactor.core.publisher.Mono;

/**
 * 用户管理业务接口
 */
public interface UserSerivice {
    Mono<User> getUserById(Long id);
}
