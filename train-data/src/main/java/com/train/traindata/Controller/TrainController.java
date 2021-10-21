package com.train.traindata.Controller;
import com.train.traindata.Entity.Seat;

import java.time.LocalTime;
import com.train.traindata.Service.TrainService;
import com.train.traindata.Entity.CancelDummyTicket;
import com.train.traindata.Entity.DummySeat;
import com.train.traindata.Entity.DummyTrain;
import com.train.traindata.Entity.Train;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainController {
    
    private static int seatId = 0;

    @Autowired
    private TrainService trainS;

    @PostMapping("add/train")
    public void addTrain(@RequestBody DummyTrain train )
    {
        Train t = Train.builder()
                        .trainId(train.getTrainId())
                        .trainName(train.getTrainName())
                        .source(train.getSource())
                        .destination(train.getDestination())
                        .arrivalTime(LocalTime.of(10,10))
                        .reachingTime(LocalTime.of(20, 20))
                        .fare(train.getFare())
                        .build();
        
        trainS.savingTrainData(t);
    }
 
    
    @PostMapping(value="/bookseat")
    public void BookSeat(@RequestBody DummySeat ds) {
        
      trainS.bookTickets(ds);
    }
 
    
    @GetMapping("/search/seat/{trainId}")
    public void searchSeat(@PathVariable("trainId") int trainId)
    {
        int[][] availableSeats = trainS.SearchSeat(trainId);
        
        for(int i = 0 ; i < availableSeats.length; i++)
        {
            for(int j = 0 ; j < availableSeats[i].length; j++)
            {
                System.out.println(availableSeats[i][j] + "  ");
            }
            System.out.println();
        }
    }

    @PostMapping("/cancel")
    public void cancelTicket(@RequestBody CancelDummyTicket ticket)
    {
        trainS.cancelTickets(ticket);
    }
}
