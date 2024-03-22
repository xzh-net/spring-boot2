package net.xzh.swagger.modules.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import net.xzh.swagger.modules.model.UmsResourceCategory;

/**
 * 后台资源分类管理Service
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
