package net.xzh.sharding.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.sharding.mapper.OrderMapper;
import net.xzh.sharding.model.Order;
import net.xzh.sharding.service.IOrderService;

/**
 * @author vjsp
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}