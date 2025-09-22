package net.xzh.swagger.modules.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import net.xzh.swagger.modules.model.UmsResource;

/**
 * 后台资源管理Service
 */
public interface UmsResourceService extends IService<UmsResource> {
    /**
     * 添加资源
     */
    boolean create(UmsResource umsResource);

    /**
     * 修改资源
     */
    boolean update(Long id, UmsResource umsResource);

    /**
     * 删除资源
     */
    boolean delete(Long id);

    /**
     * 分页查询资源
     */
    Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
