package net.xzh.gen.service;

import java.util.List;

import net.xzh.gen.dto.AdminRoleDto;
import net.xzh.gen.dto.ResourceWithCateDto;
import net.xzh.gen.dto.RoleStatDto;
import net.xzh.gen.model.UmsAdmin;

/**
 * 后台用户管理Service
 * Created by macro on 2020/12/8.
 */
public interface UmsAdminService {
    void create(UmsAdmin entity);

    void update(UmsAdmin entity);

    void delete(Long id);

    UmsAdmin select(Long id);

    List<UmsAdmin> listAll(Integer pageNum, Integer pageSize);

    List<UmsAdmin> list(Integer pageNum, Integer pageSize, String username, List<Integer> statusList);

    List<UmsAdmin> subList(Long roleId);

    List<RoleStatDto> groupList();

    void deleteByUsername(String username);

    void updateByIds(List<Long> ids,Integer status);

    AdminRoleDto selectWithRoleList(Long id);

    ResourceWithCateDto selectResourceWithCate(Long id);
}
