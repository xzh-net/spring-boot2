package net.xzh.gitlab.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * hook事件处理
 * 
 * @author CR7
 *
 */
@RestController
public class WebhookController {

	@SuppressWarnings("unchecked")
	@PostMapping("/gitlab/webhook/{id}")
	public ResponseEntity<String> receiveWebhook(@RequestHeader("X-Gitlab-Token") String token, @PathVariable String id,
			@RequestBody String payload) {
		//根据仓库id找到对应仓库的密钥，再去查询当前仓库对应的触发规则
		if (!"your-secret-token".equals(token)) {
			return ResponseEntity.status(403).body("Invalid token");
		}
		try {
			// 将payload字符串转换为Map，便于处理数据
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> data = mapper.readValue(payload, Map.class);
			System.out.println("Received webhook: " + data);
			// 在这里添加你的逻辑，例如调用构建或部署脚本等
			// 例如：deployProject(data);
			return ResponseEntity.ok("Webhook received");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error processing webhook");
		}
	}
}