package net.xzh.redis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xzh.redis.common.model.CommonPage;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.model.PmsBrand;
import net.xzh.redis.service.PmsBrandService;

/**
 * 品牌管理，如何使用缓存注解
 */

@Api(tags = "缓存")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

	@ApiOperation("获取所有品牌列表")
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	@ApiOperation("添加品牌")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		int count = brandService.createBrand(pmsBrand);
		if (count == 1) {
			LOGGER.debug("createBrand success:{}", pmsBrand);
			return CommonResult.success(pmsBrand);
		} else {
			LOGGER.debug("createBrand failed:{}", pmsBrand);
			return CommonResult.failed("操作失败");
		}
	}

	@ApiOperation("更新指定id品牌信息")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto) {
		int count = brandService.updateBrand(id, pmsBrandDto);
		if (count == 1) {
			LOGGER.debug("updateBrand success:{}", pmsBrandDto);
			return CommonResult.success(pmsBrandDto);
		} else {
			LOGGER.debug("updateBrand failed:{}", pmsBrandDto);
			return CommonResult.failed("操作失败");
		}
	}

	@ApiOperation("删除指定id的品牌")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		int count = brandService.deleteBrand(id);
		if (count == 1) {
			LOGGER.debug("deleteBrand success :id={}", id);
			return CommonResult.success(null);
		} else {
			LOGGER.debug("deleteBrand failed :id={}", id);
			return CommonResult.failed("操作失败");
		}
	}

	@ApiOperation("分页查询品牌列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<PmsBrand>> listBrand(
			@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3") @ApiParam("每页数量") Integer pageSize) {
		List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(brandList));
	}

	@ApiOperation("获取指定id的品牌详情")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.getBrand(id));
	}
	
	@RequestMapping(value = "/refreshCache", method = RequestMethod.GET)
    @ApiOperation("刷新缓存")
    public CommonResult<Boolean> refreshCache() {
		brandService.refreshCache();
        return CommonResult.success(true);
    }
}
