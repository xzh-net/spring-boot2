package net.xzh.yaml.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ModelConfig {

	@NotBlank(message = "model name不能为空")
	private String name;

	@NotBlank(message = "model不能为空")
	private String model;

	@NotBlank(message = "apiBase不能为空")
	private String apiBase;

	@NotBlank(message = "apiKey不能为空")
	private String apiKey;

	@NotNull(message = "roles不能为空")
	private List<String> roles;

	@NotNull(message = "capabilities不能为空")
	private List<String> capabilities;

	@NotBlank(message = "provider不能为空")
	private String provider;

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getApiBase() {
		return apiBase;
	}

	public void setApiBase(String apiBase) {
		this.apiBase = apiBase;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<String> capabilities) {
		this.capabilities = capabilities;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}