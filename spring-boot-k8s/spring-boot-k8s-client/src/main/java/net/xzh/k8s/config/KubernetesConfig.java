package net.xzh.k8s.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;

/**
 * k8s初始化
 * @author CR7
 *
 */
@Component
public class KubernetesConfig {

	@Value("${k8s.api-http}")
	private String httpApi;

	@Value("${k8s.token}")
	private String token;

	@Bean
	public ApiClient defaultClient() {
		ApiClient client = new ClientBuilder().setBasePath(httpApi).setVerifyingSsl(false)
				.setAuthentication(new AccessTokenAuthentication(token)).build();
		Configuration.setDefaultApiClient(client);
		return client;
	}
}
