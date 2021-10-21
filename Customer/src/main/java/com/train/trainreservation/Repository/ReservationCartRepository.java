package com.train.trainreservation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.trainreservation.Entity.ReservationCart;

@Repository
public interface ReservationCartRepository extends JpaRepository<ReservationCart,Integer> {
    
}
