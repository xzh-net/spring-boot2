package net.xzh.kaptcha.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {
    
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "160");
        // 图片高
        properties.setProperty("kaptcha.image.height", "60");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "38");
        // session key
        properties.setProperty("kaptcha.session.key", "kaptchaCode");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");
        // 干扰线颜色
        properties.setProperty("kaptcha.noise.color", "35,37,38");
        // 背景颜色渐变
        properties.setProperty("kaptcha.background.clear.from", "185,216,238");
        properties.setProperty("kaptcha.background.clear.to", "125,175,220");
        
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        
        return defaultKaptcha;
    }
}