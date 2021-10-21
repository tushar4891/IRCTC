package com.train.traindata.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.traindata.Entity.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train,Integer> {
    
}
