package net.xzh.emqx.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.xzh.emqx.enums.QosEnum;
import net.xzh.emqx.properties.MqttProperties;

/**
 * WebHook
 */
@RestController
@RequestMapping("/mqtt")
public class WebHookController {
	
	private static final Logger log = LoggerFactory.getLogger(WebHookController.class);

	private Map<String, Boolean> clientStatus = new HashMap<>();
	
	@Autowired
	private MqttProperties properties;

	@PostMapping("/v3/webhook")
	public void hook(@RequestBody Map<String, Object> params) {
		log.info("emqx 触发 webhook,请求体数据={}", params);
		String action = (String) params.get("action");
		String clientId = (String) params.get("clientid");
		if (action.equals("client_connected")) {
			log.info("客户端{}上线", clientId);
			clientStatus.put(clientId, true);
			//自动为客户端产生订阅
            autoSub(clientId,"autoSub/#",QosEnum.QoS2,true);
		}
		if (action.equals("client_disconnected")) {
			log.info("客户端{}下线", clientId);
			clientStatus.put(clientId, false);
			//自动的为客户端取消订阅
            autoSub(clientId,"autoSub/#",QosEnum.QoS2,false);
		}

	}

	@GetMapping("/allStatus")
	public Map getStatus() {
		return this.clientStatus;
	}
	
	/**
     * 自动的订阅和取消订阅
     * @param clientId
     * @param topicFilter
     * @param qos
     * @param sub
     */
    private void autoSub(String clientId, String topicFilter, QosEnum qos,boolean sub){
        RestTemplate restTemplate = new RestTemplateBuilder().basicAuthentication(properties.getUsername(), properties.getPassword())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        //封装请求参数
        Map<String,Object> params = new HashMap<>();
        params.put("clientid",clientId);
        params.put("topic",topicFilter);
        params.put("qos",qos.value());
        
        //设置头信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //设置请求体
        HttpEntity entity = new HttpEntity(params,headers);
        //发送请求
        if(sub){
            //自动订阅
            new Thread(()->{
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(properties.getApiUrl()+"/api/v4/mqtt/subscribe", entity, String.class);
                log.info("自动订阅的结果:{}",responseEntity.getBody());
            }).start();
            return;
        }
        //取消订阅
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(properties.getApiUrl()+"/api/v4/mqtt/unsubscribe", entity, String.class);
        log.info("自动取消订阅的结果:{}",responseEntity.getBody());
    }
}
