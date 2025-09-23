package net.xzh.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.xzh.security.common.model.CommonPage;
import net.xzh.security.common.model.CommonResult;
import net.xzh.security.model.PmsBrand;
import net.xzh.security.service.PmsBrandService;

/**
 * 品牌管理Controller
 */
@Api(tags = "品牌管理")
@RequestMapping("/brand")
@RestController
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;


	@ApiOperation("获取所有品牌列表")
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	@ApiOperation("添加品牌")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		return CommonResult.success(brandService.createBrand(pmsBrand));
	}

	@ApiOperation("更新指定id品牌信息")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrandDto) {
		return CommonResult.success(brandService.updateBrand(id, pmsBrandDto));
	}

	@ApiOperation("删除指定id的品牌")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.deleteBrand(id));
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
}
