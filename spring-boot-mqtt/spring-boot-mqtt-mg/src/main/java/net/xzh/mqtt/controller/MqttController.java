package net.xzh.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.mqtt.common.model.CommonResult;
import net.xzh.mqtt.gateway.MqttGateway;

/**
 * 发布测试
 */
@Api(tags = "MQTT接口")
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttGateway  mqttGateway;

	@PostMapping("/send")
    @ApiOperation("向默认主题发送消息")
    public CommonResult send(String payload) {
    	mqttGateway.sendToMqtt(payload);
        return CommonResult.success(null);
    }

	@PostMapping("/send2Topic")
    @ApiOperation("向指定主题发送消息")
    public CommonResult send2Topic(String payload, String topic) {
    	mqttGateway.sendToMqtt(payload, topic);
        return CommonResult.success(null);
    }
}
