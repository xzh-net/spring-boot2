package net.xzh.webflux.service;

import net.xzh.webflux.model.User;
import reactor.core.publisher.Mono;

/**
 * @Version: 1.0
 * @Desc:
 */
public interface UserSerivice {
    Mono<User> getUserById(Long id);
}
