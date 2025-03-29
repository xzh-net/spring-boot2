package net.xzh.sonar.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SonarApi {
	static String sonarUrl = "http://172.17.17.136:9000";
	static String sonarToken = "访问令牌";
	static String projectKey = "项目名称";

	SonarApi() {

	}

	public static void main(String[] args) {
		SonarApi sonarApi = new SonarApi();
		// 指标
		sonarApi.measuresComponent(projectKey);
		// 问题
		sonarApi.issuesSearch(projectKey);
//		// 质量阈
		sonarApi.qualityGates(projectKey);
//		// 重复度
		sonarApi.duplications(projectKey);
	}

	/**
	 * 获取度量指标 见README.md指标明细说明
	 * 
	 * @param projectKey
	 */

	public void measuresComponent(String projectKey) {
		String apiEndpoint = sonarUrl + "/api/measures/component";
		String url = apiEndpoint + "?component=" + projectKey
				+ "&metricKeys=bugs,vulnerabilities,code_smells,coverage,duplicated_lines_density&login=" + sonarToken;
		ResponseEntity<String> res = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
		System.out.println(res.getBody());
	}

	/**
	 * 重复度
	 * 
	 * @param projectKey
	 */

	public void duplications(String projectKey) {
		String apiEndpoint = sonarUrl + "/api/duplications/show";
		String url = apiEndpoint + "?key=" + projectKey + "&login=" + sonarToken;
		ResponseEntity<String> res = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
		System.out.println(res.getBody());
	}

	/**
	 * 获取质量阈
	 * 
	 * @param projectKey
	 */

	public void qualityGates(String projectKey) {
		String apiEndpoint = sonarUrl + "/api/qualitygates/project_status";
		String url = apiEndpoint + "?projectKey=" + projectKey + "&login=" + sonarToken;
		ResponseEntity<String> res = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
		System.out.println(res.getBody());
	}

	/**
	 * 检索问题列表
	 */
	public void issuesSearch(String projectKey) {
		String apiEndpoint = sonarUrl + "/api/issues/search";
		String url = apiEndpoint + "?componentKeys=" + projectKey + "&login=" + sonarToken;
		ResponseEntity<String> res = new RestTemplate().exchange(url, HttpMethod.GET, null, String.class);
		System.out.println(res.getBody());
	}

}