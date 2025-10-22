package net.xzh.mongo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.mongo.common.model.CommonPage;
import net.xzh.mongo.common.model.CommonResult;
import net.xzh.mongo.domain.MemberReadHistory;
import net.xzh.mongo.service.MemberReadHistoryService;

/**
 * 会员商品浏览记录管理
 */
@RestController
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {
	@Autowired
	private MemberReadHistoryService memberReadHistoryService;

	/**
	 * 添加浏览记录
	 * 
	 * @param memberReadHistory
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public CommonResult<?> create(@RequestBody MemberReadHistory memberReadHistory) {
		int count = memberReadHistoryService.create(memberReadHistory);
		if (count > 0) {
			return CommonResult.success(null);
		} else {
			return CommonResult.failed();
		}
	}

	/**
	 * 删除浏览记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public CommonResult<?> delete(@RequestParam("ids") List<String> ids) {
		int count = memberReadHistoryService.delete(ids);
		if (count > 0) {
			return CommonResult.success(null);
		} else {
			return CommonResult.failed();
		}
	}

	/**
	 * 清空除浏览记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public CommonResult<?> clear() {
		memberReadHistoryService.clear();
		return CommonResult.success(null);
	}

	/**
	 * 分页查询用户浏览记录
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<CommonPage<MemberReadHistory>> list(
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
		Page<MemberReadHistory> page = memberReadHistoryService.list(pageNum, pageSize);
		return CommonResult.success(CommonPage.restPage(page));
	}
}
