package net.xzh.activiti.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "xzh")
public class AuthProperties {

	private List<String> noAuthUrls = new ArrayList<>(); // noAuthUrls

}