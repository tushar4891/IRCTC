package com.train.traindata.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelDummyTicket {
    
    private int trainId;
    public int row[];
    public int column[];
}
