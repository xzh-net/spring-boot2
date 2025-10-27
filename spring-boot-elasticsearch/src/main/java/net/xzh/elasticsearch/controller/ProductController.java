package net.xzh.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.elasticsearch.common.model.CommonPage;
import net.xzh.elasticsearch.common.model.CommonResult;
import net.xzh.elasticsearch.domain.EsProduct;
import net.xzh.elasticsearch.service.ProductService;

/**
 * 商品管理
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 所有产品导入到ES
     * @return
     */
    @RequestMapping(value = "/product/importAll", method = RequestMethod.POST)
    @ResponseBody
    public String importAllList() {
        int count = productService.importAll();
        return "导入成功"+ count;
    }


    /**
     * 简单搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<EsProduct> esProductPage = productService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

    /**
     * 综合搜索（sort字段：0->按相关度；1->按新品；2->按销量；3->价格从低到高；4->价格从高到低）
     * @param keyword
     * @param brandId
     * @param productCategoryId
     * @param pageNum
     * @param pageSize
     * @param sort
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) Long brandId,
                                                      @RequestParam(required = false) Long productCategoryId,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                      @RequestParam(required = false, defaultValue = "0") Integer sort) {
        Page<EsProduct> esProductPage = productService.search(keyword, brandId, productCategoryId, pageNum, pageSize, sort);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }

}
