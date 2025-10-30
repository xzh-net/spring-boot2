package net.xzh.yaml.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * 自定义解析，不需要对应bean的属性，无法使用注解
 * 
 * @author xzh
 *
 */
@Service
public class FlexibleYamlValidationService {

	private final ObjectMapper objectMapper;

	public FlexibleYamlValidationService() {
		this.objectMapper = new ObjectMapper(new YAMLFactory());
	}

	public ValidationResult validateYaml(String yaml) {
		try {
			// 解析YAML为Map结构
			Map<String, Object> config = objectMapper.readValue(yaml, new TypeReference<Map<String, Object>>() {
			});

			List<String> errors = new ArrayList<>();

			// 验证必需字段 - 可配置化
			validateRequiredFields(config, errors);

			if (errors.isEmpty()) {
				return ValidationResult.valid(config);
			} else {
				return ValidationResult.invalid(String.join("; ", errors));
			}

		} catch (Exception e) {
			return ValidationResult.invalid("YAML格式错误: " + e.getMessage());
		}
	}

	private void validateRequiredFields(Map<String, Object> config, List<String> errors) {
		// 可配置的必需字段验证
		Map<String, String> requiredFields = new HashMap<>();
		requiredFields.put("name", "字符串");
		requiredFields.put("version", "字符串");
		requiredFields.put("schema", "字符串");
		requiredFields.put("models", "数组");

		for (Map.Entry<String, String> entry : requiredFields.entrySet()) {
			String field = entry.getKey();
			String expectedType = entry.getValue();

			if (!config.containsKey(field)) {
				errors.add("缺少必需字段: " + field);
				continue;
			}

			Object value = config.get(field);
			if (value == null || value.toString().trim().isEmpty()) {
				errors.add("字段 " + field + " 不能为空");
			}

			// 简单的类型检查
			if ("数组".equals(expectedType) && !(value instanceof List)) {
				errors.add("字段 " + field + " 必须是数组类型");
			}
		}

		// 验证models数组结构
		if (config.containsKey("models") && config.get("models") instanceof List) {
			validateModels((List<?>) config.get("models"), errors);
		}
	}

	private void validateModels(List<?> models, List<String> errors) {
		if (models.isEmpty()) {
			errors.add("models数组不能为空");
			return;
		}

		// 可配置的model必需字段
		Map<String, String> modelRequiredFields = new HashMap<>();
		modelRequiredFields.put("name", "字符串");
		modelRequiredFields.put("model", "字符串");
		modelRequiredFields.put("apiBase", "字符串");
		modelRequiredFields.put("apiKey", "字符串");
		modelRequiredFields.put("roles", "数组");
		modelRequiredFields.put("capabilities", "数组");
		modelRequiredFields.put("provider", "字符串");

		for (int i = 0; i < models.size(); i++) {
			Object modelObj = models.get(i);
			if (!(modelObj instanceof Map)) {
				errors.add("models[" + i + "] 必须是对象");
				continue;
			}

			@SuppressWarnings("unchecked")
			Map<String, Object> model = (Map<String, Object>) modelObj;

			for (Map.Entry<String, String> entry : modelRequiredFields.entrySet()) {
				String field = entry.getKey();
				String expectedType = entry.getValue();

				if (!model.containsKey(field)) {
					errors.add("model[" + i + "] 缺少必需字段: " + field);
					continue;
				}

				Object value = model.get(field);
				if (value == null || value.toString().trim().isEmpty()) {
					errors.add("model[" + i + "]." + field + " 不能为空");
				}

				// 类型检查
				if ("数组".equals(expectedType) && !(value instanceof List)) {
					errors.add("model[" + i + "]." + field + " 必须是数组类型");
				}
			}
		}
	}

	public static class ValidationResult {
		private final boolean valid;
		private final String message;
		private final Map<String, Object> config;

		private ValidationResult(boolean valid, String message, Map<String, Object> config) {
			this.valid = valid;
			this.message = message;
			this.config = config;
		}

		public static ValidationResult valid(Map<String, Object> config) {
			return new ValidationResult(true, null, config);
		}

		public static ValidationResult invalid(String message) {
			return new ValidationResult(false, message, null);
		}

		// getters
		public boolean isValid() {
			return valid;
		}

		public String getMessage() {
			return message;
		}

		public Map<String, Object> getConfig() {
			return config;
		}
	}
}