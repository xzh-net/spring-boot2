package net.xzh.openstack4j.controller;

import java.util.Arrays;

import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient.OSClientV3;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.openstack.OSFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公用连接服务
 * 
 * @author Administrator
 *
 */
@RestController
public class BaseController {

	public static OSClientV3 OSClient() {
		String endpoint = "http://172.17.19.40:5000/v3";
		String userName = "admin";
		String password = "000000";
		String tenantId = "0f86252d01d246f4bc8330dfb0d81bc9";//项目ID
		String domainId = "default";
		OSClientV3 os = OSFactory.builderV3()
				.endpoint(endpoint)
				.credentials(userName, password, Identifier.byId(domainId))
				.scopeToProject(Identifier.byId(tenantId))
				.authenticate();
		return os;
	}
	
	public static void main(String[] args) {
		String endpoint = "http://172.17.19.40:5000/v3";
		String userName = "admin";
		String password = "000000";
		String tenantId = "0f86252d01d246f4bc8330dfb0d81bc9";//项目ID
		String domainId = "default";
		OSClientV3 os = OSFactory.builderV3()
		        .endpoint(endpoint)
		        .credentials(userName, password, Identifier.byId(domainId))
		        .scopeToProject(Identifier.byId(tenantId))
		        .authenticate();
		//创建实例
		ServerCreate request = Builders.server().name("test_instances" + System.currentTimeMillis())
				.networks(Arrays.asList("bf170898-c3ce-4f05-b6ce-8e3390a6e62b"))
				.image("ed0152b5-8a4f-456e-b268-287223b5c310")//
				.flavor("lif.flavor.id").keypairName("lif-key").build();
		Server server = os.compute().servers().bootAndWaitActive(request, 600);
		//查询镜像
		os.compute().images().list();
		System.out.println(server);
	}
}