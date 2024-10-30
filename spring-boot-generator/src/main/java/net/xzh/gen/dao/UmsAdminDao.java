package net.xzh.gen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.xzh.gen.dto.AdminRoleDto;
import net.xzh.gen.dto.ResourceWithCateDto;
import net.xzh.gen.dto.RoleStatDto;
import net.xzh.gen.model.UmsAdmin;

/**
 * Created 2020/12/9.
 */
public interface UmsAdminDao {

    List<RoleStatDto> groupList();

    AdminRoleDto selectWithRoleList(@Param("id") Long id);

    List<UmsAdmin> subList(@Param("roleId") Long roleId);

    ResourceWithCateDto selectResourceWithCate(@Param("id")Long id);
}
