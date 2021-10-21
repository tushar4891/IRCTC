package com.train.trainreservation.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Data
@Builder
public class SeatLoc {
    
    @Id
    private int seat_Id;
    private int co;
    private int ro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private ReservationCart rCart;

    public SeatLoc(int seat_Id, int co, int ro, ReservationCart rCart) {
        this.seat_Id = seat_Id;
        this.co = co;
        this.ro = ro;
        this.rCart = rCart;
    }


    
}
