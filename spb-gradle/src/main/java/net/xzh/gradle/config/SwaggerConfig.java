package net.xzh.gradle.config;

import net.xzh.gradle.common.config.BaseSwaggerConfig;
import net.xzh.gradle.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("net.xzh.gradle.modules")
                .title("权限管理")
                .description("接口文档")
                .contactName("xzh")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
