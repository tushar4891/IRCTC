package com.train.traindata.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.train.traindata.Entity.CancelDummyTicket;
import com.train.traindata.Entity.DummySeat;
import com.train.traindata.Entity.DummyTrain;
import com.train.traindata.Entity.ReservationStatusEvent;
import com.train.traindata.Entity.Train;
import com.train.traindata.Enum.ReservationStatus;
import com.train.traindata.Repository.SeatRepository;
import com.train.traindata.Repository.TrainRepository;
import com.train.traindata.Entity.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainService {
    
    @Autowired
    private TrainRepository trainRepo;

    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    KafkaTemplate<String,ReservationStatusEvent> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, DummySeat> kafkaTemplate2;

    public void savingTrainData(Train dTrain)
    {
        trainRepo.save(dTrain);
    }
 
    public void savingSeatData(Seat s)
    {
         seatRepo.save(s);
    }

    public int [][] SearchSeat(int trainId)
    {
        List<Seat> seats = seatRepo.searchByTrainId(trainId);  
        
        int [][] matrix = new int[5][5];

        for(int[] row: matrix)
            Arrays.fill(row, 0);
        
        for(int i = 0 ; i < seats.size(); i++)
        {
            int r = seats.get(i).getRow();
            int c = seats.get(i).getColumn();
            
            matrix[r][c] = 1;
        }
        return matrix;
    }

    public void cancelTickets(CancelDummyTicket ticket) {

        List<Integer> row = new ArrayList<>();
        List<Integer> col = new ArrayList<>();

        for(int i = 0 ; i < ticket.row.length; i++)
            row.add(ticket.row[i]);
        

        for(int i = 0 ; i < ticket.column.length; i++)
            col.add(ticket.column[i]);
        
        for(int i = 0 ; i < row.size(); i++)
        {
            int r = row.get(i);
            int c = col.get(i);

            List<Seat> s = seatRepo.searchSeatByRowAndColumn(c, r);

            for(int k = 0 ; k < s.size(); k++)
            {
                System.out.println("ID  : " +  s.get(k).getSeatId());
            }

            for(int p = 0; p < s.size(); p++)
            {
                if(s.get(p).getTrain().getTrainId() == ticket.getTrainId())
                {
                    System.out.println("ID**** : " + s.get(p).getTrain().getTrainId());
                    System.out.println("ticket " + ticket.getTrainId());

                    seatRepo.deleteById(s.get(p).getSeatId());
                    break;
                }
            }
            
        }
    }

    public  void bookTickets(DummySeat ds)
    {
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
                ReservationStatusEvent rEvent = new ReservationStatusEvent().builder()
                                                .trainId(ds.getTrainId())
                                                .trainName(ds.getTrainName())
                                                .fare(ds.getFare())
                                                .status(ReservationStatus.NOT_AVAILABLE)
                                                .build();
                log.info("Reservation is not avaialbale for train Id {} ", ds.getTrainId());

                kafkaTemplate.send("Reservation-event-topic", rEvent.getTrainName(), rEvent);
                return;
            }
              
            ReservationStatusEvent sEvent = new ReservationStatusEvent().builder()
                                                .trainId(ds.getTrainId())
                                                .trainName(ds.getTrainName())
                                                .fare(ds.getFare())
                                                .status(ReservationStatus.AVAILABLE)
                                                .build();

            log.info("Reservation is avaialbale for train Id {} ", ds.getTrainId());

            kafkaTemplate.send("Reservation-event-topic", sEvent.getTrainName(), sEvent);

            // Passing seat data to Payment service to book the seat

            kafkaTemplate2.send("payment", ds.getTrainName(), ds);
        }
    }
}
