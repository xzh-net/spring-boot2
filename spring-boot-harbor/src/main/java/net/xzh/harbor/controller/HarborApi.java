package net.xzh.harbor.controller;

import java.util.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Harbor管理
 * 
 * @author Administrator
 *
 */
public class HarborApi {

	private HarborApi() {

	}

	// 连接 Harbor 需要设置的信息
	private static String harborUrl = "http://172.17.17.37:8088/api/v2.0";
	private static String harborUsername = "admin";
	private static String harborPassword = "LNyD2h9HSr7oZaqI";

	/**
	 * Authorization Basic认证
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@SuppressWarnings("serial")
	private HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
				set("Authorization", authHeader);
				setContentType(MediaType.APPLICATION_JSON);
			}
		};
	}

	/**
	 * 创建用户,密码必须满足复杂度
	 */
	public void createUser() {
		String user="{"
				+ "  \"comment\": \"说明\","
				+ "  \"username\": \"xuzhihao\","
				+ "  \"password\": \"123qWE!@#\","
				+ "  \"email\": \"xuzhihao@163.com\","
				+ "  \"realname\": \"徐智豪\""
				+ "}";
		HttpEntity<String> entity = new HttpEntity<>(user, createHeaders(harborUsername, harborPassword));
		ResponseEntity<String> resp = new RestTemplate().exchange(harborUrl + "/users", HttpMethod.POST, entity,
				String.class);
		System.out.println(resp.getBody());
	}

	/**
	 * 查询用户是否存在
	 * 
	 * @param username
	 * @param password
	 */
	public void users(String username, String password) {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(username, password));
		HttpEntity<String> resp = new RestTemplate().exchange(harborUrl + "/users/current", HttpMethod.GET, entity,
				String.class);
		System.out.println(resp.getBody());
	}
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public void removeUser(String userId) {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(harborUsername, harborPassword));
		HttpEntity<String> resp=new RestTemplate().exchange(harborUrl + "/users/" + userId, HttpMethod.DELETE, entity, String.class);
		System.out.println(resp.getBody());
	}
	
	/**
	 * 查询所有用户
	 */
	public void listUser() {
		HttpEntity<String> entity = new HttpEntity<>(createHeaders(harborUsername, harborPassword));
		HttpEntity<String> resp=new RestTemplate().exchange(harborUrl + "/users", HttpMethod.GET, entity, String.class);
		System.out.println(resp.getBody());
	}
	
	/**
	 * 为指定项目创建一个无时效，可推拉机器人账号
	 * 
	 * name: 机器人名称，系统会自动添加robot$+${project} 前缀。
	 * duration: 过期时间（Unix时间戳，非必填），-1 永不过期
	 * level: project 固定值
	 * permissions: 权限列表，每个对象需指定：
	 *   namespace: 项目id
	 *   kind: project 固定值
	 *   access: 权限组
	 *     resource: 资源类型
	 *     action: 操作类型
	 */
	public void createRebot(String project) {
		String rebot = "{"
				+ "    \"name\": \"rebot2025\", "
				+ "    \"duration\": -1, "
				+ "    \"description\": \"这是一个机器人账号\", "
				+ "    \"disable\": false, "
				+ "    \"level\": \"project\", "
				+ "    \"permissions\": ["
				+ "        {"
				+ "            \"namespace\": \"k13iwh8l\", "
				+ "            \"kind\": \"project\", "
				+ "            \"access\": ["
				+ "                {"
				+ "                    \"resource\": \"repository\", "
				+ "                    \"action\": \"pull\""
				+ "                }, "
				+ "                {"
				+ "                    \"resource\": \"repository\", "
				+ "                    \"action\": \"push\""
				+ "                }"
				+ "            ]"
				+ "        }"
				+ "    ]"
				+ "}";
		HttpEntity<String> entity = new HttpEntity<>(rebot, createHeaders(harborUsername, harborPassword));
		ResponseEntity<String> resp = new RestTemplate().exchange(harborUrl + "/robots", HttpMethod.POST, entity,
				String.class);
		System.out.println(resp.getBody());
	}
	
	/**
	 * 为指定项目创建一个webhook
	 * @param project
	 */
	public void createWebhook(String project) {
		String rebot = "{"
				+ "	\"enabled\": true,"
				+ "	\"event_types\": [\"DELETE_ARTIFACT\", \"PULL_ARTIFACT\", \"PUSH_ARTIFACT\", \"TAG_RETENTION\"],"
				+ "	\"targets\": [{"
				+ "		\"type\": \"http\","
				+ "		\"address\": \"http://172.17.17.165:8080/harbor/webhook\","
				+ "		\"skip_cert_verify\": true,"
				+ "		\"payload_format\": \"Default\","
				+ "		\"auth_header\": \"123456\""
				+ "	}],"
				+ "	\"name\": \"webhook2025\","
				+ "	\"description\": \"这是一个测试webhook\""
				+ "}";
		HttpEntity<String> entity = new HttpEntity<>(rebot, createHeaders(harborUsername, harborPassword));
		ResponseEntity<String> resp = new RestTemplate().exchange(harborUrl + "/projects/16/webhook/policies", HttpMethod.POST, entity,
				String.class);
		System.out.println(resp.getBody());
	}
	
	public static void main(String[] args) {
		HarborApi api = new HarborApi();
		// 创建用户
//		api.createUser();
		// 查询用户
//		api.users("xuzhihao", "123qWE!@#");
		// 删除用户
//		api.removeUser("37");
		// 查询所有用户
//		api.listUser();
		// 创建机器人
//		api.createRebot("k13iwh8l");
		// 创建webhook
		api.createWebhook("");
	}

}