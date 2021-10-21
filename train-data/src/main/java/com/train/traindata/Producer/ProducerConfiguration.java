package com.train.traindata.Producer;

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

import java.util.HashMap;
import java.util.Map;

import com.train.traindata.Entity.DummySeat;
import com.train.traindata.Entity.ReservationStatusEvent;

@Configuration
public class ProducerConfiguration {
    
    @Bean
    public ProducerFactory<String,ReservationStatusEvent> producerFactory1()
    {
        Map<String,Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean 
    public KafkaTemplate<String,ReservationStatusEvent> kafkaTemplate1()
    {
        return new KafkaTemplate(producerFactory1());
    }


    @Bean
    public ProducerFactory<String,DummySeat> producerFactory2()
    {
        Map<String,Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean 
    public KafkaTemplate<String,DummySeat> kafkaTemplate2()
    {
        return new KafkaTemplate(producerFactory2());
    }


  
}
