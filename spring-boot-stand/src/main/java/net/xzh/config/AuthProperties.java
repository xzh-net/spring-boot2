package net.xzh.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "xzh")
public class AuthProperties {

	private List<String> noAuthUrls = new ArrayList<>(); // noAuthUrls

	public List<String> getNoAuthUrls() {
		return noAuthUrls;
	}

	public void setNoAuthUrls(List<String> noAuthUrls) {
		this.noAuthUrls = noAuthUrls;
	}
	

}