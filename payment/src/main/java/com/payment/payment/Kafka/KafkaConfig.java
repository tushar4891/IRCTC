package com.payment.payment.Kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;


public class KafkaConfig {
    
    @Bean
    public NewTopic Payment()
    {
        return TopicBuilder.name("payment-topic").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic Seat()
    {
        return TopicBuilder.name("seat-topic").partitions(1).replicas(1).build();
    }
}
