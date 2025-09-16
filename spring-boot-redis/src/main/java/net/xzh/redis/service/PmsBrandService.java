package net.xzh.redis.service;

import java.util.List;

import net.xzh.redis.model.PmsBrand;

/**
 * 品牌
 */

public interface PmsBrandService {

	List<PmsBrand> listAllBrand();

	int createBrand(PmsBrand brand);

	int updateBrand(Long id, PmsBrand brand);

	int deleteBrand(Long id);

	List<PmsBrand> listBrand(int pageNum, int pageSize);

	PmsBrand getBrand(Long id);

	void refreshCache();

}
