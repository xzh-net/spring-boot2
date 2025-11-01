package net.xzh.rabbitmq.config.exchange;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.rabbitmq.common.constant.CommonConstant;

/**
 * 路由模式直接交换机配置
 */
@Configuration
public class DirectExchangeConfig {

    // 队列名称常量
    public static final String DIRECT_QUEUE_1 = "direct.queue1";
    public static final String DIRECT_QUEUE_2 = "direct.queue2";

    // 路由键常量
    public static final String ROUTING_KEY_ERROR = "error";
    public static final String ROUTING_KEY_WARN = "warn";
    public static final String ROUTING_KEY_INFO = "info";
    public static final String ROUTING_KEY_DEBUG = "debug";

    /**
     * 创建直接交换机
     * 直接交换机：根据精确的路由键匹配进行消息路由
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(CommonConstant.EXCHANGE_DIRECT);
    }

    /**
     * 直接队列1 - 用于处理error和warn级别的日志
     * 绑定路由键：error, warn
     */
    @Bean
    public Queue directQueue1() {
        // 参数说明：队列名称, 是否持久化, 是否独占, 是否自动删除
        return new Queue(DIRECT_QUEUE_1, true, false, false);
    }

    /**
     * 直接队列2 - 用于处理info和debug级别的日志
     * 绑定路由键：info, debug
     */
    @Bean
    public Queue directQueue2() {
        return new Queue(DIRECT_QUEUE_2, true, false, false);
    }

    /**
     * 绑定队列1到直接交换机 - 错误级别路由
     * 路由键：error
     */
    @Bean
    public Binding directBindingError(DirectExchange directExchange, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1)
                .to(directExchange)
                .with(ROUTING_KEY_ERROR);
    }

    /**
     * 绑定队列1到直接交换机 - 警告级别路由
     * 路由键：warn
     */
    @Bean
    public Binding directBindingWarn(DirectExchange directExchange, Queue directQueue1) {
        return BindingBuilder.bind(directQueue1)
                .to(directExchange)
                .with(ROUTING_KEY_WARN);
    }

    /**
     * 绑定队列2到直接交换机 - 信息级别路由
     * 路由键：info
     */
    @Bean
    public Binding directBindingInfo(DirectExchange directExchange, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2)
                .to(directExchange)
                .with(ROUTING_KEY_INFO);
    }

    /**
     * 绑定队列2到直接交换机 - 调试级别路由
     * 路由键：debug
     */
    @Bean
    public Binding directBindingDebug(DirectExchange directExchange, Queue directQueue2) {
        return BindingBuilder.bind(directQueue2)
                .to(directExchange)
                .with(ROUTING_KEY_DEBUG);
    }
}