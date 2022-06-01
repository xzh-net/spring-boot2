package net.xzh.cxf.client;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;


/**
 * 客户端拦截器(在xml片段中加头部)
 */
public class AddHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage>{
    
	private String userName;
	private String password;
	
	public AddHeaderInterceptor(String userName, String password) {
		super(Phase.PREPARE_SEND);
		this.userName = userName;
		this.password = password; 
	}
 
	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		List<Header> headers = msg.getHeaders();
		//创建Document对象
		Document doc = DOMUtils.createDocument();
		Element element = doc.createElement("authHeader"); 
		//配置服务器端Head信息的用户密码
		Element userNameElement= doc.createElement("userName"); 
		userNameElement.setTextContent(userName);
		Element passwordElement = doc.createElement("password"); 
		passwordElement.setTextContent(password);
		
		element.appendChild(userNameElement);
		element.appendChild(passwordElement);
		headers.add(new Header(new QName(""), element));
	}
}
