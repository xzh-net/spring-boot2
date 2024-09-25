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

	public static ComputeService computeService() {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, "project:xuchaoguo_project");// 项目名
		ComputeService compute = ContextBuilder.newBuilder("openstack-nova").endpoint("http://172.17.19.30:5000/v3")
				.credentials("Default:xuchaoguo", "000000")// Domain Name:userName,password
				.overrides(overrides).buildView(ComputeServiceContext.class).getComputeService();
		return compute;
	}

	public static NovaApi novaApi() {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, "project:xuchaoguo_project");// 项目名
		NovaApi novaApi = ContextBuilder.newBuilder("openstack-nova").endpoint("http://172.17.19.30:5000/v3")
				.credentials("Default:xuchaoguo", "000000").overrides(overrides)
				.modules(ImmutableSet.of(new SLF4JLoggingModule())).buildApi(NovaApi.class);
		return novaApi;
	}
	
	public static NeutronApi neutronApi() {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, "project:xuchaoguo_project");// 项目名
		NeutronApi neutronApi = ContextBuilder.newBuilder("openstack-neutron").endpoint("http://172.17.19.30:5000/v3")
				.credentials("Default:xuchaoguo", "000000").overrides(overrides)
				.modules(ImmutableSet.of(new SLF4JLoggingModule())).buildApi(NeutronApi.class);
		return neutronApi;
	}
	

	public static void main(String[] args) {
		final Properties overrides = new Properties();
		overrides.put(KeystoneProperties.KEYSTONE_VERSION, "3");
		overrides.put(KeystoneProperties.SCOPE, "project:xuchaoguo_project");// 项目名
		NovaApi novaApi = ContextBuilder.newBuilder("openstack-nova").endpoint("http://172.17.19.30:5000/v3")
				.credentials("Default:xuchaoguo", "000000").overrides(overrides)
				.modules(ImmutableSet.of(new SLF4JLoggingModule())).buildApi(NovaApi.class);
		System.out.println(novaApi);
		
		ComputeService compute = ContextBuilder.newBuilder("openstack-nova").endpoint("http://172.17.19.30:5000/v3")
				.credentials("Default:xuchaoguo", "000000")// Domain Name:userName,password
				.overrides(overrides).buildView(ComputeServiceContext.class).getComputeService();
		System.out.println(compute);
	}
}