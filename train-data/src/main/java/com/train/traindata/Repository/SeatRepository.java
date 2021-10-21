package com.train.traindata.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.train.traindata.Entity.Seat;
import java.util.*;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{
    
  @Query(value="SELECT * FROM Seat  WHERE train_train_id = ?1" , nativeQuery=true)
  public List<Seat> searchByTrainId(int trainId);

  @Query("Select s From Seat s  Where s.column = ?1 and s.row = ?2 ")
  public List<Seat> searchSeatByRowAndColumn(int c, int r);

   //public Seat findByRowAndColumn(int c, int r, int id);

  // @Query("Select s From Seat s Where s.row = ?1 and s.column = ?2 and train_train_id = ?3")
  // public Optional<Seat> searchSeatByRowAndColumnAndTrain(int r, int c, int trainId);

  @Query(value = "Select * From Seat  Where row_number = ?1 and col_number = ?2 and train_train_id = ?3 ",nativeQuery=true)
  public Optional<Seat> checkSeatAvailability(int r, int c, int trainId);

  // public Optional<Seat> findByRowAndColumnAndTrainId(int r, int c, int trainId);

}
