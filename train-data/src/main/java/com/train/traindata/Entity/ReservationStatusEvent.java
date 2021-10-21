package com.train.traindata.Entity;

import com.train.traindata.Enum.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationStatusEvent {
    
    public int trainId;
    public String trainName;
    public int fare;
    public ReservationStatus status;
    public int customerId;
    public String message;
}
