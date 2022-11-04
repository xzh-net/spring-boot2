package net.xzh.sharding.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.xzh.sharding.model.User;

/**
 * @author vjsp
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
