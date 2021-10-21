package com.train.trainreservation.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.train.trainreservation.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    
    @Query("Select c from Customer c where c.customerId = ?1")
    public Customer getCustomerById(int id);
}
