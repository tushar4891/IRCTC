package com.train.trainreservation.Entity;

import com.train.trainreservation.Enum.ReservationStatus;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationCart {
    
    @Id
    private int cart_id;
    private int trainId;
    private String trainName;
    private String source;
    private String destination;
    private int fare;
    private LocalDate date;
    private LocalTime time;
  
    @Enumerated(EnumType.STRING)
    private ReservationStatus rStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rCart", cascade = CascadeType.ALL)
    private List<SeatLoc> seatLoc;

    public ReservationCart(int cart_id) {
        this.cart_id = cart_id;
    }
}
