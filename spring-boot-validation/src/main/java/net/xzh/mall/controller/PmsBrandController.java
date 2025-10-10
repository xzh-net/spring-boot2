package net.xzh.mall.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.mall.common.model.CommonPage;
import net.xzh.mall.common.model.CommonResult;
import net.xzh.mall.model.PmsBrand;
import net.xzh.mall.service.PmsBrandService;

/**
 * 品牌管理
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
	
	@Autowired
	private PmsBrandService brandService;

	/**
	 * 查询所有列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	/**
	 * 新增品牌
	 * 
	 * @param pmsBrand
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public CommonResult<?> createBrand(@Valid @RequestBody PmsBrand pmsBrand) {
		brandService.createBrand(pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	/**
	 * 修改品牌
	 * 
	 * @param id
	 * @param pmsBrand
	 * @return
	 */
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @Valid @RequestBody PmsBrand pmsBrand) {
		brandService.updateBrand(id, pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	/**
	 * 删除品牌
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		brandService.deleteBrand(id);
		return CommonResult.failed("操作成功");
	}

	/**
	 * 分页查询所有列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<PmsBrand>> listBrand(
			@RequestParam(value = "pageNum", defaultValue = "1") @Param("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3") @Param("每页数量") Integer pageSize) {
		List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(brandList));
	}

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.getBrand(id));
	}
}
