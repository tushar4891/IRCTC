package com.payment.payment.Kafka;

import org.springframework.context.annotation.Configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import com.payment.payment.Entity.DummySeat;
import com.payment.payment.Entity.PaymentEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfiguration {
    
    @Bean
    public ProducerFactory<String,DummySeat> producerFactory1()
    {
        Map<String,Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean 
    public KafkaTemplate<String,DummySeat> kafkaTemplate1()
    {
        return new KafkaTemplate(producerFactory1());
    }

    @Bean
    public ProducerFactory<String,PaymentEvent> producerFactory2()
    {
        Map<String,Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean 
    public KafkaTemplate<String,PaymentEvent> kafkaTemplate2()
    {
        return new KafkaTemplate(producerFactory2());
    }
}
