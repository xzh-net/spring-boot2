package net.xzh.geode.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.xzh.geode.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {}
