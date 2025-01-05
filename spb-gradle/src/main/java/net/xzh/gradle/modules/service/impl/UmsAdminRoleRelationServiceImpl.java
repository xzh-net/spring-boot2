package net.xzh.gradle.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xzh.gradle.modules.mapper.UmsAdminRoleRelationMapper;
import net.xzh.gradle.modules.model.UmsAdminRoleRelation;
import net.xzh.gradle.modules.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {
}
