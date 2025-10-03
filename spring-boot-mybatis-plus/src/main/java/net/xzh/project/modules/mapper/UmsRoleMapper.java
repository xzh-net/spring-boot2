package net.xzh.project.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import net.xzh.project.modules.model.UmsRole;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 获取用户所有角色
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

}
