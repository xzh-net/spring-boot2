package net.xzh.pulsar.controller;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.pulsar.common.model.CommonResult;
import net.xzh.pulsar.dto.MessageDto;

/**
 * 功能测试
 */
@Api(tags = "PulsarController", description = "Pulsar功能测试")
@RestController
@RequestMapping("/pulsar")
public class PulsarController {

    @Autowired
    private PulsarTemplate<MessageDto> template;
    
    @Autowired
    private PulsarTemplate<String> simpleTemplate;

    @ApiOperation("发送bootTopic消息")
    @RequestMapping(value = "/bootTopic", method = RequestMethod.POST)
    public CommonResult<?> bootTopic(@RequestBody MessageDto message) {
    	try {
			template.send("bootTopic",message);
		} catch (PulsarClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return CommonResult.success(null);
    }
    
    @ApiOperation("发送stringTopic消息")
    @RequestMapping(value = "/stringTopic", method = RequestMethod.POST)
    public CommonResult<?> stringTopic(@RequestParam String msg) {
    	try {
    		simpleTemplate.send("stringTopic",msg);
		} catch (PulsarClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return CommonResult.success(null);
    }
}
