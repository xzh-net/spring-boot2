package net.xzh.rabbit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.rabbit.common.model.CommonResult;
import net.xzh.rabbit.gateway.MqttGateway;

/**
 * 发布测试
 */
@Api(tags = "MQTT接口")
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway  mqttGateway;

    @SuppressWarnings("rawtypes")
	@PostMapping("/send2DefaultTopic")
    @ApiOperation("向默认主题发送消息")
    public CommonResult send2DefaultTopic(String payload) {
    	mqttGateway.sendToMqtt(payload);
        return CommonResult.success(null);
    }

    @SuppressWarnings("rawtypes")
	@PostMapping("/send2Topic")
    @ApiOperation("向指定主题发送消息")
    public CommonResult send2Topic(String payload, String topic) {
    	mqttGateway.sendToMqtt(payload, topic);
        return CommonResult.success(null);
    }
}
