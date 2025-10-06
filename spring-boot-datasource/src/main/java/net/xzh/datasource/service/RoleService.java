package net.xzh.datasource.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.xzh.datasource.model.Role;

/**
 * 角色管理
 * @author CR7
 *
 */
public interface RoleService extends IService<Role> {
	
	
	public List<Role> list();

	public List<Role> list_slave();
	
}
