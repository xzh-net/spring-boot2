package net.xzh.cxf.common.domain;

import java.util.HashMap;

import lombok.Data;
import net.xzh.cxf.common.domain.SoapParseBean;

/**
 * cxf系统调用类属性PO，系统加载的时候读取配置文件，按照po格式封装配置文件的属性
 * 
 * @author xucg 2013-7-18
 * 
 */
@Data
public class SoapParseBeanMapping {
	private HashMap<String, SoapParseBean> functionMap = new HashMap<String, SoapParseBean>();

	public SoapParseBeanMapping(HashMap<String, SoapParseBean> functionMap) {
		this.functionMap = functionMap;
	}
}
