package net.xzh.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.xzh.activiti.model.User;

@Mapper
public interface UserMapper {

	User selectByPrimaryKey(Integer userId);
	
	/**
	 * 添加用戶
	 */
	int insert(User user);

	/**
	 * 获取所有用户
	 */
	List<User> selectAllWithDept(User userQuery);

	/**
     * 新增时用来检测用户名是否重复.
     */
    int countByUserName(@Param("username") String username);
    
    /**
     * 修改时用来检测用户名是否重复 (不包含某用户 ID).
     */
    int countByUserNameNotIncludeUserId(@Param("username") String username, @Param("userId") Integer userId);
    
    
    int updateByPrimaryKeySelective(User user);
    
    /**
     * 查询此用户拥有的所有角色的 ID
     *
     * @param userId 用户 ID
     * @return 拥有的角色 ID 数组
     */
    Integer[] selectRoleIdsByUserId(@Param("userId") Integer userId);
    
    /**
     * 更改用户的状态为某项值
     */
    int updateStatusByPrimaryKey(@Param("id") Integer id, @Param("status") int status);
    
    int updatePasswordByUserId(@Param("userId") Integer userId, @Param("password") String password, @Param("salt") String salt);
    
    int deleteByPrimaryKey(Integer userId);
}