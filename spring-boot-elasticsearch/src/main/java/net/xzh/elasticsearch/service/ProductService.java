package net.xzh.elasticsearch.service;

import org.springframework.data.domain.Page;

import net.xzh.elasticsearch.domain.EsProduct;

/**
 * 商品上下架搜索
 */
public interface ProductService {
    /**
     * 所有产品导入到ES
     */
    int importAll();

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索名称或者副标题复合查询
     */
    Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize,Integer sort);

}
