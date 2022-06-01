package net.xzh.dubbo.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import net.xzh.dubbo.service.TicketService;

/**
 * 
 * @author Administrator
 *
 */
@Component
@DubboService
public class TickerServiceImpl implements TicketService {
	@Override
	public String getTicket(String userName) {
		return userName+": <厉害了，我的国》";
	}
}
