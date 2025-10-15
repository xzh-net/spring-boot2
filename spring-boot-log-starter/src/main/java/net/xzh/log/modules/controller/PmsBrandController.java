package net.xzh.log.modules.controller;

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

import net.xzh.log.aspect.annotation.AuditLog;
import net.xzh.log.model.BusinessType;
import net.xzh.log.modules.common.model.CommonPage;
import net.xzh.log.modules.common.model.CommonResult;
import net.xzh.log.modules.model.PmsBrand;
import net.xzh.log.modules.service.PmsBrandService;
import net.xzh.log.utils.PointUtil;

/**
 * 品牌管理
 */
@RestController
@RequestMapping("/brand")
public class PmsBrandController {
	@Autowired
	private PmsBrandService brandService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

	/**
	 * 查询所有列表
	 * @return
	 */
	@AuditLog(operation = "查询所有品牌", businessType = BusinessType.SELECT)
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public CommonResult<List<PmsBrand>> getBrandList() {
		return CommonResult.success(brandService.listAllBrand());
	}

	/**
	 * 新增品牌
	 * @param pmsBrand
	 * @return
	 */
	@AuditLog(operation = "'添加品牌:' + #pmsBrand.name", businessType = BusinessType.INSERT)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public CommonResult<?> createBrand(@RequestBody PmsBrand pmsBrand) {
		brandService.createBrand(pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	/**
	 * 修改品牌
	 * @param id
	 * @param pmsBrand
	 * @return
	 */
	@AuditLog(operation = "'更新品牌:' + #pmsBrand.name", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public CommonResult<?> updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrand) {
		brandService.updateBrand(id, pmsBrand);
		PointUtil.info("user_id", "updateBrand", "updateBrand=" + pmsBrand);
		return CommonResult.success(pmsBrand);
	}

	/**
	 * 删除品牌
	 * @param id
	 * @return
	 */
	@AuditLog(operation = "'删除品牌:' + #id", businessType = BusinessType.UPDATE)
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public CommonResult<?> deleteBrand(@PathVariable("id") Long id) {
		brandService.deleteBrand(id);
		LOGGER.debug("deleteBrand id={}", id);
		return CommonResult.failed("操作成功");
	}

	/**
	 * 分页查询所有列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@AuditLog(operation = "分页查询品牌", businessType = BusinessType.SELECT)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<PmsBrand>> listBrand(
			@RequestParam(value = "pageNum", defaultValue = "1") @Param("页码") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "3") @Param("每页数量") Integer pageSize) {
		List<PmsBrand> brandList = brandService.listBrand(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(brandList));
	}

	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	@AuditLog(operation = "'查询品牌:' + #id", businessType = BusinessType.SELECT)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CommonResult<PmsBrand> brand(@PathVariable("id") Long id) {
		return CommonResult.success(brandService.getBrand(id));
	}
}
