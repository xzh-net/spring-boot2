package net.xzh.datasource.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.xzh.datasource.model.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stopping
 * @since 2024-05-13
 */
public interface UserInfoService extends IService<UserInfo> {
    public List<UserInfo> testQueryWrapper(int age);
}
