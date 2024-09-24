package net.xzh.jclouds;

import java.io.IOException;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

public class JCloudsNova {

	public static void main(String[] args) throws IOException {
		Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());

		String provider = "openstack-nova";
		String identity = "admin:admin"; // 项目管理员和用户
		String credential = "000000";// 用户密码

	ContextBuilder.newBuilder(provider).endpoint("http://172.17.19.30:5000/v2.0")// API地址
				.credentials(identity, credential).modules(modules).buildApi(NovaApi.class);
	
	
	}

}