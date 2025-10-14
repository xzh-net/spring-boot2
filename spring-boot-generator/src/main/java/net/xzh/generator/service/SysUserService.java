package net.xzh.generator.service;

import net.xzh.generator.model.po.SysUserPO;
import net.xzh.generator.model.vo.view.SysUserVO;
import net.xzh.generator.model.vo.query.SysUserQueryVO;
import net.xzh.generator.model.vo.form.SysUserFormVO;
import net.xzh.generator.model.vo.list.SysUserListVO;
import net.xzh.generator.common.model.api.CommonPage;
import net.xzh.generator.framework.service.SuperService;

import java.util.List;

/**
 * 用户管理服务接口
 *
 * @author xzh
 * @date 2025-10-14 19:45:23
 */
public interface SysUserService extends SuperService<SysUserPO> {

    /**
     * 保存用户管理
     *
     * @param form 用户管理表单数据
     */
    void save(SysUserFormVO form);

    /**
     * 更新用户管理
     *
     * @param id   用户管理记录ID
     * @param form 用户管理表单数据
     */
    void edit(Long id, SysUserFormVO form);

    /**
     * 获取用户管理
     *
     * @param id 用户管理记录ID
     * @return SysUserVO 用户管理详情信息
     */
    SysUserVO get(Long id);

    /**
     * 分页查询用户管理列表
     *
     * @param query 用户管理查询条件
     * @return CommonPage<SysUserVO> 用户管理分页列表
     */
    CommonPage<SysUserListVO> page(SysUserQueryVO query);

    /**
     * 查询所有用户管理
     *
     * @param query 用户管理查询条件
     * @return List<SysUserVO> 用户管理列表
     */
    List<SysUserListVO> listAll(SysUserQueryVO query);

}

