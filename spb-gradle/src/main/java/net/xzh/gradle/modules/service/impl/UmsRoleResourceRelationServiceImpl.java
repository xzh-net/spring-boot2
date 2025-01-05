package net.xzh.gradle.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.xzh.gradle.modules.mapper.UmsRoleResourceRelationMapper;
import net.xzh.gradle.modules.model.UmsRoleResourceRelation;
import net.xzh.gradle.modules.service.UmsRoleResourceRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色资源关系管理Service实现类
 * Created by macro on 2020/8/21.
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements UmsRoleResourceRelationService {
}
