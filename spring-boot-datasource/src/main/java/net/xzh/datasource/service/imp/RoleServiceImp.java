package net.xzh.datasource.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.datasource.mapper.RoleMapper;
import net.xzh.datasource.model.Role;
import net.xzh.datasource.service.RoleService;

/**
 * 角色管理
 * @author CR7
 *
 */

@Service
@DS("master")
public class RoleServiceImp extends ServiceImpl<RoleMapper, Role> implements RoleService {
    
	@Resource
    RoleMapper roleMapper;
    
    @Override
    public List<Role> list() {
        QueryWrapper<Role> userQueryWrapper = new QueryWrapper<>();
        List<Role> userList = roleMapper.selectList(userQueryWrapper);
        return userList;
    }

	@Override
	@DS("slave_1")
	public List<Role> list_slave() {
		QueryWrapper<Role> userQueryWrapper = new QueryWrapper<>();
        List<Role> userList = roleMapper.selectList(userQueryWrapper);
        return userList;
	}
    
    

}
