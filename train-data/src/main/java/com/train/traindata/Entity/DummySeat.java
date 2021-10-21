package com.train.traindata.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DummySeat {
    
    public int trainId;
    public String trainName;
    public int row[];
    public int column[];
    public String source;
    public String destination;
    public int fare;
    public int customerId;
}
