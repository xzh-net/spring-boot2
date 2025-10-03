package net.xzh.pulsar.controller;

import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import net.xzh.pulsar.dto.MessageDto;

/**
 * 消息生产者
 */
@RequestMapping("/pulsar")
@RestController
public class ProducerController {

    @Autowired
    private PulsarTemplate<MessageDto> template;
    
    @Autowired
    private PulsarTemplate<String> stringTemplate;

    /**
     * 发送系统消息
     * @param msg
     * @return
     */
    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public String bootTopic(@RequestBody MessageDto message) {
    	try {
			template.send("systemTopic",message);
		} catch (PulsarClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "ok";
    }
    /**
     * 发送用户消息
     * @param msg
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String stringTopic(@RequestParam String msg) {
    	try {
    		stringTemplate.send("userTopic",msg);
		} catch (PulsarClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "ok";
    }
}
