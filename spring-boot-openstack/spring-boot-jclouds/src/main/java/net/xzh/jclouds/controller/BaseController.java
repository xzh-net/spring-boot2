package net.xzh.jclouds.controller;

import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.config.KeystoneProperties;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableSet;

/**
 * 公用连接服务
 * 
 * @author Administrator
 *
 */
@RestController
public class BaseController {
	
	static String project="project:admin";// 项目名
	static String identity="http://172.17.19.49:5000/v3";
	static String domain= "Default";// Domain
	static String userName= "admin"; // userName
	static String password="000000"; // password
	
	

	public static ComputeService computeService() {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, project);
		ComputeService compute = ContextBuilder.newBuilder("openstack-nova").endpoint(identity)
				.credentials(domain+":"+userName, password)
				.overrides(overrides).buildView(ComputeServiceContext.class).getComputeService();
		return compute;
	}

	public static NovaApi novaApi() {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, project);
		NovaApi novaApi = ContextBuilder.newBuilder("openstack-nova").endpoint(identity)
				.credentials(domain+":"+userName, password).overrides(overrides)
				.modules(ImmutableSet.of(new SLF4JLoggingModule())).buildApi(NovaApi.class);
		return novaApi;
	}

	public static NeutronApi neutronApi() {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, project);
		NeutronApi neutronApi = ContextBuilder.newBuilder("openstack-neutron").endpoint(identity)
				.credentials(domain+":"+userName, password).overrides(overrides)
				.modules(ImmutableSet.of(new SLF4JLoggingModule())).buildApi(NeutronApi.class);
		return neutronApi;
	}
}