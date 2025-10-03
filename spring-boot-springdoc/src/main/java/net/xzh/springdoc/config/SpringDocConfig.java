package net.xzh.springdoc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfig {

    @Value("${app.info.title:API Documentation}")
    private String title;
    
    @Value("${app.info.description:API Documentation}")
    private String description;
    
    @Value("${app.info.version:1.0.0}")
    private String version;
    
    @Value("${app.info.contact.name:}")
    private String contactName;
    
    @Value("${app.info.contact.email:}")
    private String contactEmail;
    
    @Value("${app.info.contact.url:}")
    private String contactUrl;
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(apiInfo());
    }
    
    
    private Info apiInfo() {
        Contact contact = new Contact();
        if (contactName != null && !contactName.isEmpty()) {
            contact.setName(contactName);
        }
        if (contactEmail != null && !contactEmail.isEmpty()) {
            contact.setEmail(contactEmail);
        }
        if (contactUrl != null && !contactUrl.isEmpty()) {
            contact.setUrl(contactUrl);
        }
        
        return new Info()
                .title(title)
                .description(description)
                .version(version)
                .contact(contact)
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));
    }
}