package com.train.trainreservation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.trainreservation.Entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    
}
