package net.xzh.mq.domain;

import lombok.Data;

//订单
@Data
public class Order {
	Long oid;// 订单id

	// 用户
	Integer uid;// 用户id
	String username;// 用户名

	// 商品
	Integer pid;// 商品id
	String pname;// 商品名称
	Double pprice;// 商品单价

	// 数量
	Integer number;// 购买数量
}