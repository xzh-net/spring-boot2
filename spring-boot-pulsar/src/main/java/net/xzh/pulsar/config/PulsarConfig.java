package net.xzh.pulsar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.majusko.pulsar.producer.ProducerFactory;
import net.xzh.pulsar.dto.MessageDto;

/**
 * Pulsar配置类
 */
@Configuration
public class PulsarConfig {
    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer("bootTopic", MessageDto.class)
                .addProducer("stringTopic", String.class);
    }
}
