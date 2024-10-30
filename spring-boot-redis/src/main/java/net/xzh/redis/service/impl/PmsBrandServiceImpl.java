package net.xzh.redis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.xzh.redis.mapper.PmsBrandMapper;
import net.xzh.redis.model.PmsBrand;
import net.xzh.redis.model.PmsBrandExample;
import net.xzh.redis.service.PmsBrandService;

/**
 * PmsBrandService实现类
 * Created 2019/4/19.
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
	
	 /**
     * redis数据库自定义key
     */
    public  static final String REDIS_KEY_DATABASE="mall:brand";
    
    @Autowired
    private PmsBrandMapper brandMapper;
    
    @Override
    public int create(PmsBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @CacheEvict(value = REDIS_KEY_DATABASE, key = "#id")
    @Override
    public int update(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @CacheEvict(value = REDIS_KEY_DATABASE, key = "#id")
    @Override
    public int delete(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Cacheable(value = REDIS_KEY_DATABASE, key = "#id", unless = "#result==null")
    @Override
    public PmsBrand getItem(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public List<PmsBrand> ListAll() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

}
