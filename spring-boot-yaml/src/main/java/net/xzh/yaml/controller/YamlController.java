package net.xzh.yaml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.yaml.service.FlexibleYamlValidationService;
import net.xzh.yaml.service.YamlValidationService;

/**
 * yaml格式校验
 * @author xzh
 *
 */
@RestController
@RequestMapping("/api/yaml")
public class YamlController {

    @Autowired
    private YamlValidationService yamlValidationService;
    
    @Autowired
    private FlexibleYamlValidationService flexibleYamlValidationService;

    /**
     * 结构化成Bean校验
     * @param yaml
     * @return
     */
    @PostMapping("/validate")
    public String validateBean(@RequestBody String yaml) {
        YamlValidationService.ValidationResult result = yamlValidationService.validateYaml(yaml);
        if (result.isValid()) {
            return "YAML格式正确";
        } else {
            return "YAML格式错误: " + result.getMessage();
        }
    }
    
    /**
     * 使用Map获取字段自定义校验
     * @param yaml
     * @return
     */
    @PostMapping("/validateMap")
    public String validateMap(@RequestBody String yaml) {
    	FlexibleYamlValidationService.ValidationResult result = flexibleYamlValidationService.validateYaml(yaml);
        if (result.isValid()) {
            return "YAML格式正确";
        } else {
            return "YAML格式错误: " + result.getMessage();
        }
    }
}