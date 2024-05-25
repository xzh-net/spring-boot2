package net.xzh.emqx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 认证，授权，判断超级用户
 * 
 * @author admin
 *
 */
@RestController
@RequestMapping("/mqtt")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private HashMap<String, String> users;

	@PostConstruct
	public void init() {
		users = new HashMap<>();
		users.put("user", "123456");
		users.put("emq-client2", "123456");// testtopic/#
		users.put("emq-client3", "123456");// testtopic/123
		users.put("admin", "admin");
	}

	@PostMapping("/v4/auth")
	public ResponseEntity auth4(@RequestParam("clientid") String clientid, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("ipaddr") String ipaddr) {
		log.info("emqx http认证请求,clientid={},username={},password={},ipaddr={}", clientid, username, password, ipaddr);
		String value = users.get(username);
		if (StringUtils.isEmpty(value)) {
			return ResponseEntity.status(HttpStatus.OK).body("ignore");
		}
		if (!value.equals(password)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity(HttpStatus.OK);

	}

	@PostMapping("/v4/superuser")
	public ResponseEntity superuser(@RequestParam("clientid") String clientid,
			@RequestParam("username") String username, @RequestParam("ipaddr") String ipaddr) {
		log.info("emqx 超级用户请求,clientid={},username={},ipaddr={}", clientid, username, ipaddr);
		if (clientid.contains("admin") || username.contains("admin")) {
			log.info("用户{}是超级用户", username);
			return new ResponseEntity(HttpStatus.OK);
		} else {
			log.info("用户{}不是超级用户", username);
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping("/v4/acl")
	public ResponseEntity acl(@RequestParam("access") int access, @RequestParam("username") String username,
			@RequestParam("clientid") String clientid, @RequestParam("ipaddr") String ipaddr,
			@RequestParam("topic") String topic, @RequestParam("mountpoint") String mountpoint) {
		log.info("emqx acl请求,access={},username={},clientid={},ipaddr={},topic={},mountpoint={}", access, username,
				clientid, ipaddr, topic, mountpoint);
		if (username.equals("emq-client2") && topic.equals("testtopic/#") && access == 1) {
			log.info("客户端{}有权限订阅{}", username, topic);
			return new ResponseEntity(HttpStatus.OK);
		}
		if (username.equals("emq-client3") && topic.equals("testtopic/123") && access == 2) {
			log.info("客户端{}有权限向{}发布消息", username, topic);
			return new ResponseEntity(HttpStatus.OK);
		}
		log.info("客户端{},username={},没有权限对主题{}进行{}操作", clientid, username, topic, access == 1 ? "订阅" : "发布");
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/v5/auth")
	public ResponseEntity auth5(@RequestBody Map<String, Object> params) {
		String username = (String) params.get("username");
		String value = users.get(username);
		
		if (true) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		
		HashMap<String, Object> rtn = new HashMap<>();
		rtn.put("result", "allow");
		rtn.put("is_superuser", true);
		return new ResponseEntity<>(rtn, HttpStatus.OK);

	}
}
