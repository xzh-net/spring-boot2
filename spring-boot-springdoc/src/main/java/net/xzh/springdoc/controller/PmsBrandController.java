package net.xzh.springdoc.controller;

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
import net.xzh.springdoc.common.model.CommonPage;
import net.xzh.springdoc.common.model.CommonResult;
import net.xzh.springdoc.model.PmsBrand;
import net.xzh.springdoc.service.PmsBrandService;

/**
 * 商品品牌管理
 */
@Tag(name = "商品品牌管理", description = "商品品牌管理相关的API")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;

	@Operation(summary = "获取所有品牌列表", description = "获取所有品牌列表")
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	@Operation(summary = "添加品牌", description = "添加品牌")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		int rtn = brandService.createBrand(pmsBrand);
		return CommonResult.success(rtn);
	}

	@Operation(summary = "修改品牌", description = "更新指定id品牌信息")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto) {
		int rtn = brandService.updateBrand(id, pmsBrandDto);
		return CommonResult.success(rtn);
	}

	@Operation(summary = "删除品牌", description = "删除指定id的品牌")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		int rtn = brandService.deleteBrand(id);
		return CommonResult.success(rtn);
	}

	@Operation(summary = "分页查询所有列表", description = "分页查询所有列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<CommonPage<PmsBrand>> listBrand(
			@RequestParam(value = "pageNum", defaultValue = "1") @Param("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10") @Param("每页数量") Integer pageSize) {
		List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(brandList));
	}

	@Operation(summary = "查询详情", description = "获取指定id的品牌详情")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.getBrand(id));
	}
}
