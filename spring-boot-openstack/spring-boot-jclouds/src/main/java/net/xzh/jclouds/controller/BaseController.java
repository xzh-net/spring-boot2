package net.xzh.jclouds.controller;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.config.KeystoneProperties;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.regionscoped.RegionAndId;
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
		ComputeService computeService = ContextBuilder.newBuilder("openstack-nova")
				.endpoint("http://172.17.19.30:5000/v3").credentials("Default:xuchaoguo", "000000")// Name:userName,password
				.overrides(overrides).buildView(ComputeServiceContext.class).getComputeService();

		// 自定义网络参数
		TemplateOptions options = TemplateOptions.Builder
				.networks(Arrays.asList("3a8b775d-7e0b-4244-ae8a-a92bebd5003f"));
		// 实例类型
		RegionAndId regionAndId = RegionAndId.fromRegionAndId("RegionOne", "lif.flavor.id");
		// 实例模板
		Template template = computeService.templateBuilder().locationId("RegionOne").osNameMatches(".*cirros*")
				.hardwareId(regionAndId.slashEncode()).options(options).build();
		try {
			Set<? extends NodeMetadata> nodes = computeService.createNodesInGroup("test-vnc", 1, template);
			System.out.println(nodes);
		} catch (RunNodesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}