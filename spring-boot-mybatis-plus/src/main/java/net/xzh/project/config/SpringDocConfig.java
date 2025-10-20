package net.xzh.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfig {

    @Value("${token.header}")
	private String header;
    
    @Value("${token.prefix}")
	private String prefix;
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            //哪些要求bearerAuth认证
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
            		//定义一个bearerAuth的安全方案
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .name(header)
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .description("请输入JWT Token（格式: " + prefix + " {token}）")
                )
            );
    }
}