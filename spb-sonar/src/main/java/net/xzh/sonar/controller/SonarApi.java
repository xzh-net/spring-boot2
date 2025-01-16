package net.xzh.sonar.controller;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SonarApi {
	static String sonarqubeUrl = "http://172.17.17.136:9000";
	static String projectKey = "test";
	static String token = "8392a26df9d7714e95068703d61aa6fd2bc95cf1";

	SonarApi() {

	}

	public static void main(String[] args) {
		SonarApi sonarApi = new SonarApi();
		// 指标
		sonarApi.measuresComponent(projectKey);
		// 问题
//		sonarApi.issuesSearch(projectKey);
//		// 质量阈
//		sonarApi.qualityGates(projectKey);
//		// 重复度
//		sonarApi.duplications(projectKey);
	}

	/**
	 * 获取度量指标
	 * 见README.md指标明细说明
	 * @param projectKey
	 */

	public void measuresComponent(String projectKey) {
		String apiEndpoint = sonarqubeUrl + "/api/measures/component";
		String url = apiEndpoint + "?component=" + projectKey
				+ "&metricKeys=bugs,vulnerabilities,code_smells,coverage,duplicated_lines_density&login=" + token;
		RemoteApi(url);
	}

	/**
	 * 重复度
	 * 
	 * @param projectKey
	 */

	public void duplications(String projectKey) {
		String apiEndpoint = sonarqubeUrl + "/api/duplications/show";
		String url = apiEndpoint + "?key=" + projectKey + "&login=" + token;
		RemoteApi(url);
	}

	/**
	 * 获取质量阈
	 * 
	 * @param projectKey
	 */

	public void qualityGates(String projectKey) {
		String apiEndpoint = sonarqubeUrl + "/api/qualitygates/project_status";
		String url = apiEndpoint + "?projectKey=" + projectKey + "&login=" + token;
		RemoteApi(url);
	}

	/**
	 * 检索问题列表
	 */
	public void issuesSearch(String projectKey) {
		String apiEndpoint = sonarqubeUrl + "/api/issues/search";
		String url = apiEndpoint + "?componentKeys=" + projectKey + "&login=" + token;
		RemoteApi(url);
	}

	/**
	 * 公用调用
	 * 
	 * @param url
	 */
	private void RemoteApi(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet request = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(request);
			try {
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String result = EntityUtils.toString(entity);
						System.out.println(result);
					}
				} else {
					System.out.println(
							"Failed to fetch report. HTTP response code: " + response.getStatusLine().getStatusCode());
				}
			} finally {
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}