package net.xzh.generator.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import net.xzh.generator.model.vo.query.SysUserQueryVO;
import net.xzh.generator.model.vo.list.SysUserListVO;
import net.xzh.generator.model.vo.form.SysUserFormVO;
import net.xzh.generator.model.vo.view.SysUserVO;
import net.xzh.generator.service.SysUserService;
import net.xzh.generator.common.model.CommonPage;
import net.xzh.generator.common.model.CommonResult;
import net.xzh.generator.common.model.BusinessType;
import net.xzh.generator.framework.aspectj.annotation.AuditLog;

/**
 * 用户管理控制层
 *
 * @author xzh
 * @date 2025-10-15 16:53:14
 */
@RestController
@RequestMapping("/sysuser")
@Tag(name = "用户管理", description = "用户管理API")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

     /**
     * 保存用户管理
     *
     * @param form 用户管理表单数据
     * @return CommonResult<?> 通用返回结果
     */
    @PostMapping("")
    @Operation(summary = "新增", description = "新增")
    @AuditLog(operation = "'新增用户管理:' + #form", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('sysUser:save')")
    public CommonResult<?> save(@RequestBody @Validated SysUserFormVO form) {
        sysUserService.save(form);
        return CommonResult.success(null);
    }

    /**
     * 更新用户管理
     *
     * @param id   用户管理记录ID
     * @param form 用户管理表单数据
     * @return CommonResult<?> 通用返回结果
     */
    @PutMapping("{id}")
    @Operation(summary = "修改", description = "修改")
    @AuditLog(operation = "'修改用户管理:' + #form", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('sysUser:edit')")
    public CommonResult<?> edit(@PathVariable Long id, @RequestBody SysUserFormVO form) {
        sysUserService.edit(id, form);
        return CommonResult.success(null);
    }

    /**
     * 根据主键删除用户管理数据
     *
     * @param id 用户管理记录ID
     * @return CommonResult<?> 通用返回结果
     */
    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    @AuditLog(operation = "'删除用户管理:' + #id", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('sysUser:remove')")
    public CommonResult<?> remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return CommonResult.success(null);
    }

    /**
     * 批量删除用户管理数据
     *
     * @param ids 用户管理记录ID数组
     * @return CommonResult<?> 通用返回结果
     */
    @DeleteMapping("batchRemove")
    @Operation(summary = "批量删除", description = "批量删除")
    @AuditLog(operation = "批量删除用户管理", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('sysUser:batchRemove')")
    public CommonResult<?> batchRemove(@RequestBody Long[] ids) {
        sysUserService.removeByIds(Arrays.asList(ids));
        return CommonResult.success(null);
    }

    /**
     * 通过主键获取用户管理详情
     *
     * @param id 用户管理记录ID
     * @return CommonResult<sysUserVO> 用户管理详情返回结果
     */
    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    @AuditLog(operation = "'查询用户管理详情:' + #id", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('sysUser:get')")
    public CommonResult<SysUserVO> get(@PathVariable Long id) {
        return CommonResult.success(sysUserService.get(id));
    }

    /**
     * 分页查询用户管理列表
     *
     * @param query 用户管理查询条件
     * @return CommonResult<CommonPage < sysUserVO>> 用户管理分页列表返回结果
     */
    @GetMapping("")
    @Operation(summary = "分页查询列表", description = "分页查询列表")
    @AuditLog(operation = "'分页查询用户管理列表:' + #query", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('sysUser:list')")
    public CommonResult<CommonPage<SysUserListVO>> page(SysUserQueryVO query) {
        return CommonResult.success(sysUserService.page(query));
    }

    /**
     * 查询用户管理全部数据
     *
     * @return CommonResult<List<SysUserListVO>> 所有用户管理数据列表返回结果
     */
    @GetMapping("listAll")
    @Operation(summary = "查询全部数据", description = "查询全部数据")
    @AuditLog(operation = "'查询用户管理全部数据:' + #query", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('sysUser:listAll')")
    public CommonResult<List<SysUserListVO>> listAll(SysUserQueryVO query) {
        return CommonResult.success(sysUserService.listAll(query));
    }
}
