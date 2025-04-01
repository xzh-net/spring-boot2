package net.xzh.minio.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
public class RabbitMQConfig {
 
    @Bean
    public Queue bucketqueue() {
        return new Queue("bucketqueue", true); // 创建一个队列，false表示队列是否持久化
    }
 
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("bucketevents"); // 创建一个直接交换器
    }
 
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("bucketlogs"); // 绑定队列到交换器，并指定路由键
    }
}