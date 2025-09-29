package net.xzh.security.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.security.common.model.CommonPage;
import net.xzh.security.common.model.CommonResult;
import net.xzh.security.model.PmsBrand;
import net.xzh.security.service.PmsBrandService;

/**
 * 品牌管理
 */
@Tag(name = "品牌管理", description = "品牌管理相关的API")
@RequestMapping("/brand")
@RestController
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;


	@Operation(summary = "查询所有列表", description = "查询所有品牌列表")
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	@Operation(summary = "新增品牌", description = "添加品牌")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		return CommonResult.success(brandService.createBrand(pmsBrand));
	}

	@Operation(summary = "修改品牌", description = "更新指定id品牌信息")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto) {
		return CommonResult.success(brandService.updateBrand(id, pmsBrandDto));
	}

	@Operation(summary = "删除品牌", description = "删除指定id的品牌")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.deleteBrand(id));
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
}
