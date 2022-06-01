package net.xzh.activiti.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import net.xzh.activiti.common.exception.ApiException;
import net.xzh.activiti.common.model.ResultCode;
import net.xzh.activiti.mapper.UserMapper;
import net.xzh.activiti.mapper.UserRoleMapper;
import net.xzh.activiti.model.User;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserRoleMapper userRoleMapper;

	public List<User> selectAllWithDept(int page, int rows, User userQuery) {
		PageHelper.startPage(page, rows);
		return userMapper.selectAllWithDept(userQuery);
	}

	@Transactional
	public void add(User user, Integer[] roleIds) {
		checkUserNameExistOnCreate(user.getUsername());
		userMapper.insert(user);
		insertUserRole(user.getUserId(), roleIds);
	}

	/**
	 * 新增时校验用户名是否重复
	 * 
	 * @param username 用户名
	 */
	public void checkUserNameExistOnCreate(String username) {
		if (userMapper.countByUserName(username) > 0) {
			throw new ApiException(ResultCode.A0111);
		}
	}

	public User selectOne(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public boolean update(User user, Integer[] roleIds) {
		checkUserNameExistOnUpdate(user);
		insertUserRole(user.getUserId(), roleIds);
		return userMapper.updateByPrimaryKeySelective(user) == 1;
	}

	public void insertUserRole(Integer userId, Integer[] roleIds) {
		if (roleIds == null || roleIds.length == 0) {
			throw new IllegalArgumentException("赋予的角色数组不能为空.");
		}
		// 清空原有的角色, 赋予新角色.
		userRoleMapper.deleteUserRoleByUserId(userId);
		userRoleMapper.insertList(userId, roleIds);
	}

	public void checkUserNameExistOnUpdate(User user) {
		if (userMapper.countByUserNameNotIncludeUserId(user.getUsername(), user.getUserId()) > 0) {
			throw new ApiException(ResultCode.A0111);
		}
	}

	public Integer[] selectRoleIdsById(Integer userId) {
		return userMapper.selectRoleIdsByUserId(userId);
	}

	public boolean disableUserByID(Integer id) {
		return userMapper.updateStatusByPrimaryKey(id, 0) == 1;
	}

	public boolean enableUserByID(Integer id) {
		return userMapper.updateStatusByPrimaryKey(id, 1) == 1;
	}

	@Transactional
	public void delete(Integer userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		if ("admin".equals(user.getUsername())) {
			throw new ApiException("禁止删除超级管理员.");
		}
		userMapper.deleteByPrimaryKey(userId);
		userRoleMapper.deleteUserRoleByUserId(userId);
	}

	public void updatePasswordByUserId(Integer userId, String password) {
		userMapper.updatePasswordByUserId(userId, password, System.currentTimeMillis() + "");
	}
}