package net.xzh.cxf.common.domain;

import lombok.Data;

@Data
public class SoapParseBean {
	private String funcId;// 功能点id
	private String beanId;// 功能点注册信息
	private String beanMethod;// bean 方法
}