package net.xzh.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.xzh.mall.i18n.MyLocaleResolver;

@SpringBootApplication
public class ValidApplication {
    public static void main(String[] args) {
        SpringApplication.run(ValidApplication.class, args);
    }
    
    /**
     * 国际化
     * @return
     */
    @Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}
}
