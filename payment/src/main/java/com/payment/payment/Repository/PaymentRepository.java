package com.payment.payment.Repository;

import com.payment.payment.Entity.PaymentData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentData, Integer> {
    
    
}
