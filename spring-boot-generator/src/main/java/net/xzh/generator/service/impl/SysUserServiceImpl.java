package net.xzh.generator.service.impl;

import org.springframework.stereotype.Service;
import net.xzh.generator.common.model.api.CommonPage;
import net.xzh.generator.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.xzh.generator.framework.service.impl.SuperServiceImpl;
import net.xzh.generator.mapper.SysUserMapper;
import net.xzh.generator.service.SysUserService;
import net.xzh.generator.model.po.SysUserPO;
import net.xzh.generator.model.vo.view.SysUserVO;
import net.xzh.generator.model.vo.query.SysUserQueryVO;
import net.xzh.generator.model.vo.list.SysUserListVO;
import net.xzh.generator.model.vo.form.SysUserFormVO;
import net.xzh.generator.model.convert.SysUserConvert;

import java.util.List;

/**
 * 用户管理服务接口实现
 *
 * @author xzh
 * @date 2025-10-14 20:48:36
 */
@Service
public class SysUserServiceImpl extends SuperServiceImpl<SysUserMapper, SysUserPO> implements SysUserService {

    /**
     * 保存用户管理
     *
     * @param form 用户管理表单数据
     */
    @Override
    public void save(SysUserFormVO form) {
        save(SysUserConvert.INSTANCE.convert(form));
    }

    /**
     * 更新用户管理
     *
     * @param id   用户管理记录ID
     * @param form 用户管理表单数据
     */
    @Override
    public void edit(Long id, SysUserFormVO form) {
        SysUserPO sysUserPo = SysUserConvert.INSTANCE.convert(form);
        sysUserPo.setId(id);
        updateById(sysUserPo);
    }

    /**
     * 获取用户管理详情
     *
     * @param id 用户管理记录ID
     * @return SysUserVO 用户管理详情信息
     */
    @Override
    public SysUserVO get(Long id) {
        return SysUserConvert.INSTANCE.convert(getById(id));
    }

    /**
     * 分页查询用户管理列表
     *
     * @param query 用户管理查询条件
     * @return CommonPage<SysUserListVO> 用户管理分页列表
     */
    @Override
    public CommonPage<SysUserListVO> page(SysUserQueryVO query) {
        LambdaQueryWrapper<SysUserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper                .like(StringUtils.isNotEmpty(query.getUsername()), SysUserPO::getUsername, query.getUsername())                .like(StringUtils.isNotEmpty(query.getPassword()), SysUserPO::getPassword, query.getPassword())                .like(StringUtils.isNotEmpty(query.getNickname()), SysUserPO::getNickname, query.getNickname())                .like(StringUtils.isNotEmpty(query.getHeadimgurl()), SysUserPO::getHeadimgurl, query.getHeadimgurl())                .like(StringUtils.isNotEmpty(query.getMobile()), SysUserPO::getMobile, query.getMobile())                .like(StringUtils.isNotEmpty(query.getEmail()), SysUserPO::getEmail, query.getEmail())                .eq(query.getAccountBalance() != null, SysUserPO::getAccountBalance, query.getAccountBalance())                .eq(query.getStatus() != null, SysUserPO::getStatus, query.getStatus())                .eq(query.getRegistDate() != null, SysUserPO::getRegistDate, query.getRegistDate())                .eq(query.getSortBy() != null, SysUserPO::getSortBy, query.getSortBy())        ;
        Page<SysUserPO> page = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<SysUserPO> iPage = page(page, queryWrapper);
        return CommonPage.restPage(iPage, SysUserConvert.INSTANCE.convertList(iPage.getRecords()));
    }

    /**
     * 查询所有用户管理列表
     *
     * @param query 用户管理查询条件
     * @return List<SysUserListVO> 用户管理列表
     */
    @Override
    public List<SysUserListVO> listAll(SysUserQueryVO query) {
        LambdaQueryWrapper<SysUserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper                .like(StringUtils.isNotEmpty(query.getUsername()), SysUserPO::getUsername, query.getUsername())                .like(StringUtils.isNotEmpty(query.getPassword()), SysUserPO::getPassword, query.getPassword())                .like(StringUtils.isNotEmpty(query.getNickname()), SysUserPO::getNickname, query.getNickname())                .like(StringUtils.isNotEmpty(query.getHeadimgurl()), SysUserPO::getHeadimgurl, query.getHeadimgurl())                .like(StringUtils.isNotEmpty(query.getMobile()), SysUserPO::getMobile, query.getMobile())                .like(StringUtils.isNotEmpty(query.getEmail()), SysUserPO::getEmail, query.getEmail())                .eq(query.getAccountBalance() != null, SysUserPO::getAccountBalance, query.getAccountBalance())                .eq(query.getStatus() != null, SysUserPO::getStatus, query.getStatus())                .eq(query.getRegistDate() != null, SysUserPO::getRegistDate, query.getRegistDate())                .eq(query.getSortBy() != null, SysUserPO::getSortBy, query.getSortBy())        ;
        List<SysUserPO> list = list(queryWrapper);
        return SysUserConvert.INSTANCE.convertList(list);
    }
    
}