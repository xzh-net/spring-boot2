package net.xzh.fisco.config;

import java.util.List;
import java.util.Map;

import org.fisco.bcos.sdk.config.model.AmopTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "fisco")
public class FiscoCnfig {
    private Map<String, Object> cryptoMaterial;
    public Map<String, List<String>> network;
    public List<AmopTopic> amop;
    public Map<String, Object> account;
    public Map<String, Object> threadPool;
}
