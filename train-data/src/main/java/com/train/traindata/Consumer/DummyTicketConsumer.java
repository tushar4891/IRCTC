package com.train.traindata.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.train.traindata.Entity.DummySeat;
import com.train.traindata.Entity.ReservationCancelEvent;
import com.train.traindata.Entity.ReservationStatusEvent;
import com.train.traindata.Entity.ReservationSuccessfulEvent;
import com.train.traindata.Entity.Seat;
import com.train.traindata.Entity.Train;
import com.train.traindata.Enum.ReservationStatus;
import com.train.traindata.Repository.SeatRepository;
import com.train.traindata.Repository.TrainRepository;
import com.train.traindata.Service.TrainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DummyTicketConsumer {
    
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TrainService trainService;

    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    KafkaTemplate<String,ReservationStatusEvent> kafkaTemplate;

    @KafkaListener(topics = "book-ticket-topic", groupId = "booking_consumer")
    public void onMessage(String ticket) throws JsonMappingException, JsonProcessingException
    {
        DummySeat dSeat = objectMapper.readValue(ticket, DummySeat.class);
        log.info("Ticket info is {} " , dSeat.getTrainId());

        log.info("Booking ticket for customer {}  ", dSeat.getCustomerId());
        log.info("Booking for train id {} ", dSeat.getTrainId());

        trainService.bookTickets(dSeat);
    }
 
    @KafkaListener(topics = "seat-topic", groupId = "seat")
    public void seatMessage(String s) throws JsonMappingException, JsonProcessingException
    {
        DummySeat ds = objectMapper.readValue(s, DummySeat.class);

        List<Integer> row = new ArrayList<>();
        List<Integer> col = new ArrayList<>();

        for(int i = 0 ; i < ds.row.length; i++)
        {
            row.add(ds.row[i]);
        }
 
        for(int i = 0 ; i < ds.column.length; i++)
        {
            col.add(ds.column[i]);
        }

        for(int i = 0 ; i < row.size(); i++)
        {
            Optional<Seat> availability = seatRepo.checkSeatAvailability(row.get(i), col.get(i), ds.getTrainId());
            
            if(availability.isPresent())
            {
                ReservationStatusEvent rsEvent = new ReservationStatusEvent().builder()
                                                .trainId(ds.getTrainId())
                                                .trainName(ds.getTrainName())
                                                .fare(ds.getFare())
                                                .status(ReservationStatus.CANCELED)
                                                .customerId(ds.getCustomerId())
                                                .message("Rolling back Payment !")
                                                .build();
                                            
                kafkaTemplate.send("Reservation-event-topic", rsEvent.getTrainName(), rsEvent);
                return;
            }
        }
            
        ReservationStatusEvent rpEvent = new ReservationStatusEvent().builder()
                                            .trainId(ds.getTrainId())
                                            .trainName(ds.getTrainName())
                                            .fare(ds.getFare())
                                            .customerId(ds.getCustomerId())
                                            .status(ReservationStatus.CONFIRMED)
                                            .message("Reservation Successfully done !")
                                            .build();      
            
        kafkaTemplate.send("Reservation-event-topic", rpEvent.getTrainName(), rpEvent);

       for(int i = 0 ; i < row.size(); i++)
       {
           int r = row.get(i);
           int c = col.get(i);
      
            Train t = new Train(ds.getTrainId());

           Seat p = new Seat().builder()
                        .row(r)
                        .column(c)
                        .train(t)
                        .build();
            
            seatRepo.save(p);
       }
    }
}
