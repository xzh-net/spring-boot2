/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.xzh.mq.dxl;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hutool.json.JSONUtil;
import net.xzh.mq.domain.OrderItem;

/**
 * Created by macro on 2020/5/19.
 */
@Component
public class DxlSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String orderId, Long delayTimes) {
		OrderItem order = new OrderItem();
		order.setId(Long.valueOf(orderId));
		order.setProductName("死信头盔" + System.currentTimeMillis());
		rabbitTemplate.convertAndSend(DxlRabbitConfig.DXL_EXCHANGE_NAME, DxlRabbitConfig.DXL_ROUTING_KEY, JSONUtil.toJsonStr(order),
				message -> {
					message.getMessageProperties().setExpiration("3000");
					return message;
				}, new CorrelationData(orderId));
	}

}
