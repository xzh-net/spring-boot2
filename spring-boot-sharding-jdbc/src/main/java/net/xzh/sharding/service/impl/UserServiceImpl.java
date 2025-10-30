package net.xzh.sharding.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.sharding.mapper.UserMapper;
import net.xzh.sharding.model.User;
import net.xzh.sharding.service.IUserService;

/**
 * @author vjsp
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}