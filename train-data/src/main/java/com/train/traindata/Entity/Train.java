package com.train.traindata.Entity;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Train {
      
    @Id
    private int trainId;
    private String trainName;
    private String source;
    private String destination;
    private LocalTime arrivalTime;
    private LocalTime reachingTime;
    private int fare;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "train")
    //@JoinColumn(name = "seat_id")
    private List<Seat> seat;

    public Train(int trainId) {
        this.trainId = trainId;
    }    
}
