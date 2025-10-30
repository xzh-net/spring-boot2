package net.xzh.yaml.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import net.xzh.yaml.model.ApplicationConfig;

@Service
public class YamlValidationService {
    
    private final ObjectMapper yamlObjectMapper;
    private final Validator validator;
    
    public YamlValidationService() {
        this.yamlObjectMapper = new ObjectMapper(new YAMLFactory());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }
    
    public ValidationResult validateYaml(String yaml) {
        try {
            // 解析YAML
            ApplicationConfig config = yamlObjectMapper.readValue(yaml, ApplicationConfig.class);
            
            // 验证对象
            Set<ConstraintViolation<ApplicationConfig>> violations = validator.validate(config);
            
            if (violations.isEmpty()) {
                return ValidationResult.valid(config);
            } else {
                StringBuilder errorMsg = new StringBuilder();
                for (ConstraintViolation<ApplicationConfig> violation : violations) {
                    errorMsg.append(violation.getPropertyPath())
                           .append(": ")
                           .append(violation.getMessage())
                           .append("; ");
                }
                return ValidationResult.invalid(errorMsg.toString());
            }
            
        } catch (Exception e) {
            return ValidationResult.invalid("YAML格式错误: " + e.getMessage());
        }
    }
    
    public static class ValidationResult {
        private final boolean valid;
        private final String message;
        private final ApplicationConfig config;
        
        private ValidationResult(boolean valid, String message, ApplicationConfig config) {
            this.valid = valid;
            this.message = message;
            this.config = config;
        }
        
        public static ValidationResult valid(ApplicationConfig config) {
            return new ValidationResult(true, null, config);
        }
        
        public static ValidationResult invalid(String message) {
            return new ValidationResult(false, message, null);
        }
        
        // getters
        public boolean isValid() { return valid; }
        public String getMessage() { return message; }
        public ApplicationConfig getConfig() { return config; }
    }
}