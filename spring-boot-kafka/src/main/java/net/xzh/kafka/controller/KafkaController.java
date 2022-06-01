package net.xzh.kafka.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.kafka.common.model.CommonResult;
import net.xzh.kafka.component.KafkaProducer;

@Api(tags = "Kafka测试")
@RestController
@SuppressWarnings("rawtypes")
public class KafkaController {

	@Autowired
	private KafkaProducer kafkaProducer;

	
	@ApiOperation("发送消息")
    @RequestMapping(value = "/sendOne", method = RequestMethod.GET)
	public CommonResult send(String msg) {
		kafkaProducer.send("topic1",msg);
		return CommonResult.success(System.currentTimeMillis());
	}
	
	@ApiOperation("批量发送消息")
    @RequestMapping(value = "/sendBatch", method = RequestMethod.GET)
	public CommonResult sendBatch(String msg) {
		ArrayList<String> msgs=new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			msgs.add(msg+i);
		}
		kafkaProducer.send("topic1",msgs);
		return CommonResult.success(System.currentTimeMillis());
	}
	
	@ApiOperation("有事务异常")
    @RequestMapping(value = "/sendByTran", method = RequestMethod.GET)
	public CommonResult sendByTran(String msg) {
		kafkaProducer.sendByTran("topic1",msg);
		return CommonResult.success(System.currentTimeMillis());
	}
	
	
	@ApiOperation("无事务异常")
    @RequestMapping(value = "/sendNoTran", method = RequestMethod.GET)
	public CommonResult sendNoTran(String msg) {
		kafkaProducer.sendNoTran("topic1", msg);
		return CommonResult.success(System.currentTimeMillis());
	}
	

}
