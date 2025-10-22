package net.xzh.mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.mongo.common.model.CommonPage;
import net.xzh.mongo.common.model.CommonResult;
import net.xzh.mongo.domain.MemberProductCollection;
import net.xzh.mongo.service.MemberCollectionService;

/**
 * 会员收藏管理
 */
@RestController
@RequestMapping("/member/productCollection")
public class MemberProductCollectionController {
	@Autowired
	private MemberCollectionService memberCollectionService;

	/**
	 * 添加商品收藏
	 * 
	 * @param productCollection
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public CommonResult<?> add(@RequestBody MemberProductCollection productCollection) {
		int count = memberCollectionService.add(productCollection);
		if (count > 0) {
			return CommonResult.success(null);
		} else {
			return CommonResult.failed();
		}
	}

	/**
	 * 删除收藏商品
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public CommonResult<?> delete(Long productId) {
		int count = memberCollectionService.delete(productId);
		if (count > 0) {
			return CommonResult.success(null);
		} else {
			return CommonResult.failed();
		}
	}

	/**
	 * 查询收藏列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<MemberProductCollection>> list(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
		Page<MemberProductCollection> page = memberCollectionService.list(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(page));
	}

	/**
	 * 显示收藏商品详情
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public CommonResult<MemberProductCollection> detail(@RequestParam Long productId) {
		MemberProductCollection memberProductCollection = memberCollectionService.detail(productId);
		return CommonResult.success(memberProductCollection);
	}

	/**
	 * 清空收藏
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public CommonResult<?> clear() {
		memberCollectionService.clear();
		return CommonResult.success(null);
	}
}
