package com.train.traindata.Entity;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DummyTrain {
    
    
    private int trainId;
    private String trainName;
    private String source;
    private String destination;
    private LocalTime arrivalTime;
    private LocalTime reachingTime;
    private int fare;

    private Seat seat;
}
