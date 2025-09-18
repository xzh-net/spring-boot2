package net.xzh.log.controller;

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
import net.xzh.log.common.aspect.annotation.AuditLog;
import net.xzh.log.common.enums.BusinessType;
import net.xzh.log.common.model.CommonPage;
import net.xzh.log.common.model.CommonResult;
import net.xzh.log.common.utils.PointUtil;
import net.xzh.log.model.PmsBrand;
import net.xzh.log.service.PmsBrandService;

/**
 * 品牌管理
 */
@Api(tags = "商品品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

	@ApiOperation("获取所有品牌列表")
	@AuditLog(operation = "查询所有品牌", businessType = BusinessType.SELECT)
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	@ApiOperation("添加品牌")
	@AuditLog(operation = "'添加品牌:' + #pmsBrand.name", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		brandService.createBrand(pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	@ApiOperation("更新指定id品牌信息")
	@AuditLog(operation = "'更新品牌:' + #pmsBrand.name", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrand) {
		brandService.updateBrand(id, pmsBrand);
		PointUtil.info("user_id", "updateBrand", "updateBrand=" + pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	@ApiOperation("删除指定id的品牌")
	@AuditLog(operation = "'删除品牌:' + #id", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		brandService.deleteBrand(id);
		LOGGER.debug("deleteBrand id={}", id);
		return CommonResult.failed("操作成功");
	}

	@ApiOperation("分页查询品牌列表")
	@AuditLog(operation = "'分页查询品牌'", businessType = BusinessType.SELECT)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<PmsBrand>> listBrand(
			@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3") @ApiParam("每页数量") Integer pageSize) {
		List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(brandList));
	}

	@ApiOperation("获取指定id的品牌详情")
	@AuditLog(operation = "'查询品牌:' + #id", businessType = BusinessType.SELECT)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.getBrand(id));
	}
}
