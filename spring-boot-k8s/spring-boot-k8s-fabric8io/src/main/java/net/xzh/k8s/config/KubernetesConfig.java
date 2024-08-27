package net.xzh.k8s.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

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
	public KubernetesClient kubernetesClient() {
        Config config = new ConfigBuilder()
                .withTrustCerts(true)
                .withMasterUrl(httpApi)
                .withOauthToken(token)
                .build();
        KubernetesClient client = new DefaultKubernetesClient(config);
		return client;
	}
}
