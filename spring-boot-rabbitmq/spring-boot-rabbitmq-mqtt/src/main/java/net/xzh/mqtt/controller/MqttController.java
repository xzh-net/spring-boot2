package net.xzh.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.mqtt.common.model.CommonResult;
import net.xzh.mqtt.gateway.IotMqttGateway;

/**
 * MQTT测试接口
 * Created by macro on 2020/9/15.
 */
@Api(tags = "MQTT测试接口")
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private IotMqttGateway iotMqttGateway;

    @SuppressWarnings("rawtypes")
	@PostMapping("/sendToDefaultTopic")
    @ApiOperation("向默认主题发送消息")
    public CommonResult sendToDefaultTopic(String payload) {
    	iotMqttGateway.sendToMqtt(payload);
        return CommonResult.success(null);
    }

    @SuppressWarnings("rawtypes")
	@PostMapping("/sendToTopic")
    @ApiOperation("向指定主题发送消息")
    public CommonResult sendToTopic(String payload, String topic) {
    	iotMqttGateway.sendToMqtt(payload, topic);
        return CommonResult.success(null);
    }
}
