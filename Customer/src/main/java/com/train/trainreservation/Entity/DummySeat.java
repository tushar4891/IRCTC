package com.train.trainreservation.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummySeat {
    
    public int trainNumber;
    public String trainName;
    public int row[];
    public int column[];
    public int trainId;
    public String source;
    public String destination;
    public int fare;
    public int customerId;
 
}
