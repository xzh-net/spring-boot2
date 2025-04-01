package net.xzh.rabbitmq.exchange;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.config.CommonConstant;

/**
 * 路由模式交换机
 */
@Configuration
public class DirectExchangeConfig {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange(CommonConstant.EXCHANGE_DIRECT);
    }

    @Bean
    public Queue directQueue1() {
        return new AnonymousQueue();
    }


    @Bean
    public Binding directBinding1a(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("error");
    }

    @Bean
    public Binding directBinding1b(DirectExchange direct, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1).to(direct).with("warn");
    }

    @Bean
    public Binding directBinding2a(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("info");
    }

    @Bean
    public Binding directBinding2b(DirectExchange direct, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2).to(direct).with("debug");
    }



}
