package net.xzh.datasource.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.datasource.component.DataSourceContextHolder;
import net.xzh.datasource.mapper.UserMapper;
import net.xzh.datasource.model.User;
import net.xzh.datasource.service.UserService;

/**
 * 用户管理
 * @author CR7
 *
 */

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    
    @Override
    public List<User> list() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(userQueryWrapper);
        return userList;
    }
    

	@Override
	public List<User> list(String dataSourceKey) {
		DataSourceContextHolder.setDataSource(dataSourceKey);
		QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List<User> userList;
		try {
			userList = userMapper.selectList(userQueryWrapper);
		} finally {
			DataSourceContextHolder.clearDataSource();
		}
        return userList;
	}
}
