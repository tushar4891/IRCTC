package com.train.traindata.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@Table(name = "seats")
public class Seat {
    
    @Id
    @GeneratedValue
    @Column(name = "seat_id", nullable = false)
    private int seatId;

    // @Column(name = "train_number")
    // private int trainNumber;

    // @Column(name = "train_name")
    // private String trainName;

    @Column(name = "row_number")
    private int row;

    @Column(name = "col_number")
    private int column;

    @ManyToOne(fetch = FetchType.LAZY)
    private Train train;
    
}
