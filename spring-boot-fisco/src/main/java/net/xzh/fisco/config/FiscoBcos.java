package net.xzh.fisco.config;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class FiscoBcos {
	@Autowired
	private FiscoCnfig fiscoConfig;

	private BcosSDK bcosSDK;

	@PostConstruct
	public void init() {
		ConfigProperty configProperty = loadProperty();
		try {
			ConfigOption configOption = new ConfigOption(configProperty, CryptoType.ECDSA_TYPE);
			bcosSDK = new BcosSDK(configOption);
		} catch (ConfigException e) {
			log.error("init error: {}", e);
		}
	}

	@SuppressWarnings("serial")
	private ConfigProperty loadProperty() {
		ConfigProperty configProperty = new ConfigProperty();
		configProperty.setCryptoMaterial(fiscoConfig.getCryptoMaterial());
		configProperty.setAccount(fiscoConfig.getAccount());
		configProperty.setNetwork(new HashMap<String, Object>() {
			{
				put("peers", fiscoConfig.getNetwork().get("peers"));
			}
		});
		configProperty.setAmop(fiscoConfig.getAmop());
		configProperty.setThreadPool(fiscoConfig.getThreadPool());
		return configProperty;

	}

}