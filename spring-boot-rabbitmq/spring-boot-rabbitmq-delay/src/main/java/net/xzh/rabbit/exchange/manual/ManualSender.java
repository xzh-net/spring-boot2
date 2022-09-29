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
package net.xzh.rabbit.exchange.manual;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManualSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RabbitMQConfirmAndReturn rabbitMQConfirmAndReturn;

	/**
	 * 开启调用失败回调函数机制
	 */
	@PostConstruct
	public void initRabbitTemplate() {
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(rabbitMQConfirmAndReturn);
		rabbitTemplate.setReturnCallback(rabbitMQConfirmAndReturn);
	}

	public void send(String orderId) {
		Message message = MessageBuilder.withBody(orderId.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").setMessageId(orderId)
				.build();
		// 构建回调返回的数据
		CorrelationData correlationData = new CorrelationData(orderId);
		// 发送消息
		rabbitTemplate.convertAndSend(ManualRabbitConfig.ITEM_TOPIC_EXCHANGE, "item.springboot-rabbitmq", message,
				correlationData);
	}
}
