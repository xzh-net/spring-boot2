package net.xzh.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import net.xzh.mall.mapper.PmsBrandMapper;
import net.xzh.mall.model.PmsBrand;
import net.xzh.mall.model.PmsBrandExample;
import net.xzh.mall.service.PmsBrandService;

/**
 * PmsBrandService
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
	@Autowired
	private PmsBrandMapper brandMapper;

	@Override
	public List<PmsBrand> listAllBrand() {
		return brandMapper.selectByExample(new PmsBrandExample());
	}

	@Override
	public int createBrand(PmsBrand brand) {
		return brandMapper.insertSelective(brand);
	}

	@Override
	public int updateBrand(Long id, PmsBrand brand) {
		brand.setId(id);
		return brandMapper.updateByPrimaryKeySelective(brand);
	}

	@Override
	public int deleteBrand(Long id) {
		return brandMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<PmsBrand> listBrand(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return brandMapper.selectByExample(new PmsBrandExample());
	}

	@Override
	public PmsBrand getBrand(Long id) {
		return brandMapper.selectByPrimaryKey(id);
	}
}
