package net.xzh.security.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.xzh.security.security.component.DynamicAccessDecisionManager;
import net.xzh.security.security.component.DynamicSecurityFilter;
import net.xzh.security.security.component.DynamicSecurityMetadataSource;
import net.xzh.security.security.component.JwtAuthenticationTokenFilter;
import net.xzh.security.security.component.RestAuthenticationEntryPoint;
import net.xzh.security.security.component.RestfulAccessDeniedHandler;
import net.xzh.security.security.util.JwtTokenUtil;
import net.xzh.security.security.util.PwdEncoderUtil;

/**
 * SpringSecurity通用配置
 * 包括通用Bean、Security通用Bean及动态权限通用Bean
 * Created by xzh on 2022/5/20.
 */
@Configuration
public class MallSecurityConfig {

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//    	return new BCryptPasswordEncoder();
    	return PwdEncoderUtil.getDelegatingPasswordEncoder("SM3");
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
