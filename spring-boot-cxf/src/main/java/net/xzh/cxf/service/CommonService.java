package net.xzh.cxf.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "commonService") // 暴露服务名称
public interface CommonService {
	@WebMethod
	@WebResult(name = "String", targetNamespace = "")
	public String doServices(@WebParam(name = "action") String action, @WebParam(name = "data") String data);

}