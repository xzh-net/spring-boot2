package net.xzh.generator.model.convert;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import net.xzh.generator.model.vo.form.SysUserFormVO;
import net.xzh.generator.model.vo.view.SysUserVO;
import net.xzh.generator.model.vo.list.SysUserListVO;
import net.xzh.generator.model.po.SysUserPO;

import java.util.List;

/**
 * 用户管理数据转换对象
 * 使用MapStruct实现对象之间的自动映射
 *
 * @author xzh
 * @date 2025-10-15 16:53:14
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);
    
    /**
     * 将表单数据转换为实体对象（保存）
     *
     * @param form 用户管理表单数据
     * @return 用户管理持久化对象
     */
    SysUserPO convert(SysUserFormVO from);
    
    /**
     * 将实体对象转换为视图对象（查询）
     *
     * @param 用户管理持久化对象
     * @return 用户管理表单数据
     */
    SysUserVO convert(SysUserPO sysUser);
    
    /**
     * 将实体对象列表转换为视图对象列表（列表）
     *
     * @param list 用户管理持久化对象列表
     * @return 用户管理列表查询对象
     */
    List<SysUserListVO> convertList(List<SysUserPO> list);
}