package net.xzh.redis.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.redis.common.model.CommonPage;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.model.PmsBrand;
import net.xzh.redis.service.PmsBrandService;

/**
 * 品牌管理，如何使用缓存注解
 */

@Tag(name = "品牌管理", description = "品牌管理API")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

	@Operation(summary = "查询所有列表", description = "查询所有品牌列表")
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	@Operation(summary = "新增品牌", description = "添加品牌")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		brandService.createBrand(pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	@Operation(summary = "修改品牌", description = "更新指定id品牌信息")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrand) {
		brandService.updateBrand(id, pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	@Operation(summary = "删除品牌", description = "删除指定id的品牌")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		brandService.deleteBrand(id);
		LOGGER.debug("deleteBrand id={}", id);
		return CommonResult.failed("操作成功");
	}

	@Operation(summary = "分页查询所有列表", description = "分页查询所有列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<PmsBrand>> listBrand(
			@RequestParam(value = "pageNum", defaultValue = "1") @Param("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3") @Param("每页数量") Integer pageSize) {
		List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(brandList));
	}

	@Operation(summary = "查询详情", description = "获取指定id的品牌详情")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.getBrand(id));
	}
	
	@Operation(summary = "刷新缓存", description = "清空指定key")
	@RequestMapping(value = "/refreshCache", method = RequestMethod.GET)
    public CommonResult<Boolean> refreshCache() {
		brandService.refreshCache();
        return CommonResult.success(true);
    }
}
