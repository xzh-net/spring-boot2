package net.xzh.mall.tiny.config;

import net.xzh.mall.tiny.common.config.BaseSwaggerConfig;
import net.xzh.mall.tiny.common.domain.SwaggerProperties;
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
                .apiBasePackage("net.xzh.mall.tiny.modules")
                .title("测试项目")
                .description("接口文档")
                .contactName("xzh")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
