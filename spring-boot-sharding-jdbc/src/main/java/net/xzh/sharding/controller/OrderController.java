package net.xzh.sharding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.sharding.model.Area;
import net.xzh.sharding.model.Order;
import net.xzh.sharding.model.User;
import net.xzh.sharding.service.IAreaService;
import net.xzh.sharding.service.IOrderService;
import net.xzh.sharding.service.IUserService;

/**
 * 订单管理
 * @author xzh
 */
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
	 * 数据初始化
	 * @return
	 */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init() {
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
		return "success";
	}

	/**
	 * 查询列表
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Order> list() {
		return orderService.list();
	}

	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order get(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    /**
     * 清除数据
     * @return
     */
    @RequestMapping(value = "/clean", method = RequestMethod.GET)
    public String clean() {
    	orderService.remove(null);
        return "success";
    }
}
