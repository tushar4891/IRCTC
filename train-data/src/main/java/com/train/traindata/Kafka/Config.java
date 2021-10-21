package com.train.traindata.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Config {
    
    @Bean
    public NewTopic generateEvent()
    {
        return TopicBuilder.name("Reservation-event-topic").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic payment()
    {
        return TopicBuilder.name("payment").partitions(1).replicas(1).build();
    }
}
