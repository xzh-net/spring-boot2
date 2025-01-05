package net.xzh.gradle.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xzh.gradle.modules.mapper.UmsRoleMenuRelationMapper;
import net.xzh.gradle.modules.model.UmsRoleMenuRelation;
import net.xzh.gradle.modules.service.UmsRoleMenuRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {
}
