package net.xzh.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.kafka.common.model.CommonResult;
import net.xzh.kafka.entity.User;

@Api(tags = "KafkaTemplate测试")
@RestController
@RequestMapping("/provider")
@Transactional(rollbackFor = RuntimeException.class)
public class KafkaController {

	@Autowired
	KafkaTemplate<Object, Object> kafkaTemplate;

	@ApiOperation("发送消息")
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public CommonResult<?> send() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("zhangsan" + Math.random());
		kafkaTemplate.send("topic1", user);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("批量发送")
	@RequestMapping(value = "/sendMultiple", method = RequestMethod.GET)
	public CommonResult<?> sendMultiple() {
		for (int i = 1; i <= 24; i++) {
			kafkaTemplate.send("topic2", "发送到Kafka的消息" + i);
		}
		return CommonResult.success(System.currentTimeMillis());
	}

}
