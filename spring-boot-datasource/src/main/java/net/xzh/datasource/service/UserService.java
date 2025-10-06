package net.xzh.datasource.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.xzh.datasource.model.User;

/**
 * 用户管理
 * @author CR7
 *
 */
public interface UserService extends IService<User> {
	
	
	public List<User> list();

	public List<User> list(String dataSourceKey);
	
}
