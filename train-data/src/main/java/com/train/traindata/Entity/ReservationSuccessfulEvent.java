package com.train.traindata.Entity;
import com.train.traindata.Enum.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSuccessfulEvent {
    
    private int trainId;
    private String trainName;
    private String message;
    private int customerId;
    private ReservationStatus status;
}
