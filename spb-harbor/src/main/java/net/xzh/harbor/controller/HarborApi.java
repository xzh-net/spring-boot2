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
		String user = String.format("{\"comment\": \"\"," + "\"email\": \"xuzhihao@163.com\","
				+ "\"password\": \"123qWE!@#\"," + "\"realname\": \"徐智豪\"," + "\"username\": \"xuzhihao\"}");
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

	public static void main(String[] args) {
		HarborApi api = new HarborApi();
		// 创建用户
//		api.createUser();
		// 查询用户
//		api.users("xuzhihao", "123qWE!@#");
		// 删除用户
//		api.removeUser("37");
		//查询所有用户
		api.listUser();

	}

}