package com.train.trainreservation.Entity;


import com.train.trainreservation.Enum.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEvent {
    
    private int trainId;
    private String trainName;
    private PaymentStatus status;
    private String message;

}
