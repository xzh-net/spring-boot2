package net.xzh.datasource.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.datasource.mapper.UserInfoMapper;
import net.xzh.datasource.model.UserInfo;
import net.xzh.datasource.service.UserInfoService;

/**
 * @author stopping
 * @since 2024-05-13
 */
@Service
@DS("master")
public class UserInfoServiceImp extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;
    @Override
    @DS("slave_1")
    public List<UserInfo> testQueryWrapper(int age) {
        QueryWrapper<UserInfo> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ge("age", age);
        List<UserInfo> userList = userInfoMapper.selectList(userQueryWrapper);
        return userList;
    }
}
