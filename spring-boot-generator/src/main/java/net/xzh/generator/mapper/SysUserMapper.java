package net.xzh.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import net.xzh.generator.framework.repository.SuperMapper;
import net.xzh.generator.model.po.SysUserPO;

/**
 * 用户管理数据库访问对象
 * 
 * @author xzh
 * @date 2025-10-14 20:48:36
 */
@Mapper
@Repository
public interface SysUserMapper extends SuperMapper<SysUserPO> {
}
