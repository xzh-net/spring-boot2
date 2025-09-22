package net.xzh.swagger.modules.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.swagger.modules.mapper.UmsResourceCategoryMapper;
import net.xzh.swagger.modules.model.UmsResourceCategory;
import net.xzh.swagger.modules.service.UmsResourceCategoryService;

/**
 * 后台资源分类管理Service实现类
 */
@Service
public class UmsResourceCategoryServiceImpl extends ServiceImpl<UmsResourceCategoryMapper,UmsResourceCategory> implements UmsResourceCategoryService {

    @Override
    public List<UmsResourceCategory> listAll() {
        QueryWrapper<UmsResourceCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(UmsResourceCategory::getSort);
        return list(wrapper);
    }

    @Override
    public boolean create(UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        return save(umsResourceCategory);
    }
}
