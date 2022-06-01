package net.xzh.mall.service;


import java.util.List;

import net.xzh.mall.model.PmsBrand;

/**
 * PmsBrandService
 * Created by xuzhihao 2019/4/19.
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrand brand);

    int updateBrand(Long id, PmsBrand brand);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}
