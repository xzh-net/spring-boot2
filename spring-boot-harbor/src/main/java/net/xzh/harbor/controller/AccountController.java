package net.xzh.harbor.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.harbor.common.model.CommonResult;
import net.xzh.harbor.model.HarborUser;

/**
 * 账号管理
 * 
 * @author Administrator
 *
 */
@Api(tags = "账号管理")
@RestController
public class AccountController {

	@Value("${harbor.username}")
	private String harborAdminUsername;
	@Value("${harbor.password}")
	private String harborAdminPassword;
	@Value("${harbor.url}")
	private String harborUrl;

	@Autowired
	RestTemplate restTemplate;

	// 重置密码
	// 查询项目仓库信息

	@ApiOperation("创建账号")
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public CommonResult<?> createUser(@RequestBody HarborUser harborUser) {
		// 设置header和认证
		HttpHeaders headers = createHeaders(harborAdminUsername, harborAdminPassword);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(JSONUtil.toJsonStr(harborUser), headers);
		ResponseEntity<String> resp = restTemplate.exchange(harborUrl + "/users", HttpMethod.POST, entity,
				new ParameterizedTypeReference<String>() {
				});
		return CommonResult.success(resp);

	}

	@ApiOperation("查询当前账号")
	@RequestMapping(value = "/getcurrentUser", method = RequestMethod.GET)
	public CommonResult<?> getcurrentUser(@RequestParam String username, @RequestParam String password) {
		// 设置header和认证
		HttpHeaders headers = createHeaders(username, password);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		HttpEntity<String> resp = restTemplate.exchange(harborUrl + "/users/current", HttpMethod.GET, entity,
				String.class);
		return CommonResult.success(JSONUtil.parseObj(resp.getBody()));
	}

	@ApiOperation("删除账号")
	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
	public CommonResult<?> deleteUser(@RequestParam String userId) {
		HttpHeaders headers = createHeaders(harborAdminUsername, harborAdminPassword);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		restTemplate.exchange(harborUrl + "/users/" + userId, HttpMethod.DELETE, entity, String.class);
		return CommonResult.success(System.currentTimeMillis());
	}

	@ApiOperation("查询所有账号")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public CommonResult<?> list() {
		HttpHeaders headers = createHeaders(harborAdminUsername, harborAdminPassword);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		HttpEntity<String> resp = restTemplate.exchange(harborUrl + "/users", HttpMethod.GET, entity, String.class);
		return CommonResult.success(resp.getBody());
	}

	/**
	 * Authorization Basic认证
	 * 
	 * @return
	 */
	@SuppressWarnings("serial")
	public static HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
				set("Authorization", authHeader);
			}
		};
	}
}