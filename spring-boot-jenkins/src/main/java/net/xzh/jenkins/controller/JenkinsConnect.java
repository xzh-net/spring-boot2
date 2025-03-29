package net.xzh.jenkins.controller;

import java.net.URI;
import java.net.URISyntaxException;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;

public class JenkinsConnect {
	
	private JenkinsConnect() {
		
	}

	// 连接 Jenkins 需要设置的信息
	private static String jenkinsUrl = "http://192.168.2.100:8080";
	private static String jenkinsUsername = "admin";
	private static String jenkinsPassword = "123456";
	
	/**
	 * Http 客户端工具
	 *
	 * 如果有些 API 该Jar工具包未提供，可以用此Http客户端操作远程接口，执行命令
	 * 
	 * @return
	 */
	public static JenkinsHttpClient getHttpClient() {
		JenkinsHttpClient jenkinsHttpClient = null;
		try {
			jenkinsHttpClient = new JenkinsHttpClient(new URI(jenkinsUrl), jenkinsUsername, jenkinsPassword);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return jenkinsHttpClient;
	}

	/**
	 * 连接 Jenkins
	 */
	public static JenkinsServer getServer() {
		JenkinsServer jenkinsServer = null;
		try {
			jenkinsServer = new JenkinsServer(new URI(jenkinsUrl), jenkinsUsername, jenkinsPassword);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return jenkinsServer;
	}
}
