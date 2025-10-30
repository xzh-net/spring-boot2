package net.xzh.yaml.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class ApplicationConfig {

	@NotBlank(message = "name不能为空")
	private String name;

	@NotBlank(message = "version不能为空")
	private String version;

	@NotBlank(message = "schema不能为空")
	private String schema;

	@Valid
	@NotEmpty(message = "models不能为空")
	private List<ModelConfig> models;

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<ModelConfig> getModels() {
		return models;
	}

	public void setModels(List<ModelConfig> models) {
		this.models = models;
	}
}
