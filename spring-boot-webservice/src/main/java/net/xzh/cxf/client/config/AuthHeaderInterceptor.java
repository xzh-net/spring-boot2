package net.xzh.cxf.client.config;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 客户端认证头拦截器 - 添加认证信息到SOAP消息头
 * 
 * @author xzh
 *
 */
public class AuthHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private String userName;
	private String password;

	public AuthHeaderInterceptor(String userName, String password) {
		super(Phase.PREPARE_SEND);// 在发送消息时执行
		this.userName = userName;
		this.password = password;
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		List<Header> headers = msg.getHeaders();
		// 创建Document对象
		Document doc = DOMUtils.createDocument();
		Element element = doc.createElement("authHeader");
		// 配置服务器端Head信息的用户密码
		Element userNameElement = doc.createElement("userName");
		userNameElement.setTextContent(userName);
		Element passwordElement = doc.createElement("password");
		passwordElement.setTextContent(password);

		element.appendChild(userNameElement);
		element.appendChild(passwordElement);
		headers.add(new Header(new QName(""), element));
	}
}
