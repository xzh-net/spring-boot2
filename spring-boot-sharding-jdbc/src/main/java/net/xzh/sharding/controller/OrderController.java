package net.xzh.sharding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.sharding.common.model.CommonResult;
import net.xzh.sharding.model.Area;
import net.xzh.sharding.model.Order;
import net.xzh.sharding.model.User;
import net.xzh.sharding.service.IAreaService;
import net.xzh.sharding.service.IOrderService;
import net.xzh.sharding.service.IUserService;

/**
 * @author xzh
 */
@Api(tags = "订单管理混合模式")
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IAreaService areaService;

	/**
	 * 初始化数据
	 */
	@ApiOperation("数据初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
	public CommonResult initDate() {
		Area area = new Area();
		area.setName("大连");
		areaService.save(area);
		String companyId;
		for (int i = 0; i < 100; i++) {
			Order u = new Order();
			if (i % 2 == 0) {
				companyId = "alibaba";
			} else {
				companyId = "baidu";
			}
			u.setCompanyId(companyId);
			u.setName(String.valueOf(i));

			User user = new User();
			user.setCompanyId(companyId);
			user.setName(String.valueOf(i));
			userService.save(user);

			u.setUserId(user.getId());
			orderService.save(u);
		}
		return CommonResult.success("success");
	}

	/**
     * 查询列表
     */
	@ApiOperation("查询列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<List<Order>> list() {
		return CommonResult.success(orderService.list());
	}

	/**
     * 查询单条记录
     */
    @ApiOperation("获取指定id的订单详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<Order> user(@PathVariable("id") Long id) {
        return CommonResult.success(orderService.getById(id));
    }

    /**
     * 清除数据
     */
    @ApiOperation("清除数据")
    @RequestMapping(value = "/clean", method = RequestMethod.GET)
    public CommonResult clean() {
    	orderService.remove(null);
        return CommonResult.success("success");
    }
}
