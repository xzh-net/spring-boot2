package net.xzh.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RabbitMQConfig {
    
    @Bean
    @Primary  // 默认实例，不设置回调
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置JSON消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 不设置回调
        return rabbitTemplate;
    }
    
    @Bean("rabbitTemplateWithCallback")
    public RabbitTemplate rabbitTemplateWithCallback(ConnectionFactory connectionFactory, RabbitMQCallback callback) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        // 设置JSON消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        
        //启用回调机制
        rabbitTemplate.setConfirmCallback(callback);
        rabbitTemplate.setReturnsCallback(callback);
        rabbitTemplate.setMandatory(true);
        
        return rabbitTemplate;
    }
}