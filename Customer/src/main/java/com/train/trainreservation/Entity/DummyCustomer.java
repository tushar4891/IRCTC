package com.train.trainreservation.Entity;
import com.train.trainreservation.Enum.AccountStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DummyCustomer {
    
    public int customerId;
    public String fname;
    public AccountStatus status;
    public int zipcode;
    public String city;
    //public ReservationCart cart;
}
