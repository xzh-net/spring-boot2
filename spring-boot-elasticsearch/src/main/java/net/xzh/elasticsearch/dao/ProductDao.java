package net.xzh.elasticsearch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.xzh.elasticsearch.domain.EsProduct;

/**
 * 搜索系统中的商品管理自定义Dao
 * Created 2018/6/19.
 */
public interface ProductDao {
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
