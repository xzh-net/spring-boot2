package net.xzh.redis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.xzh.redis.common.constant.Constants;
import net.xzh.redis.mapper.PmsBrandMapper;
import net.xzh.redis.model.PmsBrand;
import net.xzh.redis.model.PmsBrandExample;
import net.xzh.redis.service.PmsBrandService;

/**
 * PmsBrandService实现类
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
	
    
    @Autowired
    private PmsBrandMapper brandMapper;
    
    @Override
    public int createBrand(PmsBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @CacheEvict(value = Constants.CACHE_MALL_BRAND, key = "#id")
    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @CacheEvict(value = Constants.CACHE_MALL_BRAND, key = "#id")
    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(value = Constants.CACHE_MALL_BRAND, key = "#id", unless = "#result==null")
    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Cacheable(value = Constants.CACHE_MALL_BRAND, key = "'data'", unless = "#result==null")
    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    @CacheEvict(value = Constants.CACHE_MALL_BRAND, key = "'data'")
    public void refreshCache() {

    }

}
