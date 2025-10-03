package net.xzh.pulsar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.majusko.pulsar.producer.ProducerFactory;
import net.xzh.pulsar.dto.MessageDto;

/**
 * Pulsar生产者配置
 */
@Configuration
public class PulsarProducerConfig {
    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer("systemTopic", MessageDto.class)
                .addProducer("userTopic", String.class);
    }
}
