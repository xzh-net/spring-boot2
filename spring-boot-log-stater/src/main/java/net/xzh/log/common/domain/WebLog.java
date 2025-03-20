package net.xzh.log.common.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作日志 Created 2018/4/26.
 */
@Setter
@Getter
public class WebLog {

	/**
	 * 操作时间
	 */
	private LocalDateTime timestamp;
	/**
	 * 应用名
	 */
	private String applicationName;
	/**
	 * 类名
	 */
	private String className;
	/**
	 * 方法名
	 */
	private String methodName;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 租户id
	 */
	private String clientId;
	/**
	 * 操作信息
	 */
	private String operation;

	/**
	 * 总消耗时间
	 */
	private Long totalTime;

	/**
	 * 请求类型
	 */
	private String method;

	/**
	 * IP地址
	 */
	private String ip;

	/**
	 * 请求参数
	 */
	private Object parameter;

	/**
	 * 请求返回的结果
	 */
	private Object result;
}
