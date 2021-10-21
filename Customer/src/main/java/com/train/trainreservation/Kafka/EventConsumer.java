package com.train.trainreservation.Kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.traindata.Entity.ReservationStatusEvent;
import com.train.trainreservation.Entity.PaymentEvent;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventConsumer {
    
    @KafkaListener(topics = "Reservation-event-topic",  groupId = "reservation_event")
    public void listenReservationEvent(String event) throws JsonMappingException, JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        ReservationStatusEvent rEvent = mapper.readValue(event,ReservationStatusEvent.class);

        log.info("Got reservation status");
        log.info("Train name : {} ,  Status : {} ", rEvent.getTrainName(), rEvent.getStatus());
    }

    @KafkaListener(topics = "payment-topic", groupId = "pay")
    public void listenPayment(String s) throws JsonMappingException, JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        PaymentEvent pEvent = mapper.readValue(s, PaymentEvent.class);

        log.info("Got payment status ******** ");
        System.out.println(pEvent);        
    }

    
}
