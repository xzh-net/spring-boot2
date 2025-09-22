package net.xzh.swagger.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.xzh.swagger.security.component.DynamicAccessDecisionManager;
import net.xzh.swagger.security.component.DynamicSecurityFilter;
import net.xzh.swagger.security.component.DynamicSecurityMetadataSource;
import net.xzh.swagger.security.component.JwtAuthenticationTokenFilter;
import net.xzh.swagger.security.component.RestAuthenticationEntryPoint;
import net.xzh.swagger.security.component.RestfulAccessDeniedHandler;
import net.xzh.swagger.security.util.JwtTokenUtil;

/**
 * SpringSecurity通用配置
 * 包括通用Bean、Security通用Bean及动态权限通用Bean
 */
@Configuration
public class CommonSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter(){
        return new DynamicSecurityFilter();
    }
}
