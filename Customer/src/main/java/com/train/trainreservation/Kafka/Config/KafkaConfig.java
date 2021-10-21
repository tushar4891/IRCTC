package com.train.trainreservation.Kafka.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaConfig {
    
    @Bean
    public NewTopic searchTrain()
    {
        return TopicBuilder.name("book-ticket-topic").partitions(1).replicas(1).build();
    }
}
