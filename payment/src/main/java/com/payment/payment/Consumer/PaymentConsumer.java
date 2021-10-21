package com.payment.payment.Consumer;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.payment.Entity.DummySeat;
import com.payment.payment.Entity.PaymentData;
import com.payment.payment.Entity.PaymentEvent;
import com.payment.payment.Entity.ReservationStatusEvent;
import com.payment.payment.Enum.PaymentStatus;
import com.payment.payment.Enum.ReservationStatus;
import com.payment.payment.Repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentConsumer {
    
    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    KafkaTemplate<String,DummySeat> kafkaTemplate;

    @Autowired
    KafkaTemplate<String,PaymentEvent> kafkaTemplate2;

  
    @KafkaListener(topics = "payment", groupId = "payment")
    public void onMessage(String s) throws JsonMappingException, JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        DummySeat seatForPayment = mapper.readValue(s, DummySeat.class);

        Optional<PaymentData> customer = paymentRepo.findById(seatForPayment.getCustomerId());

        if(customer.isPresent())
        {
            int amount = customer.get().getBalance();
            if(amount >= seatForPayment.getFare())
            {
                customer.get().setBalance(amount - seatForPayment.getFare());
                paymentRepo.save(customer.get());

                // sending data to Seat service to book seat
                kafkaTemplate.send("seat-topic", seatForPayment.getTrainName(), seatForPayment);
                
                log.info("Payment is being done for customer id {} ", customer.get().getCustomerId());

                // create successful event and pass it to payment-topic
                PaymentEvent psEvent = new PaymentEvent().builder()
                                                .trainId(seatForPayment.getTrainId())
                                                .trainName(seatForPayment.getTrainName())
                                                .status(PaymentStatus.PAYMENT_DONE)
                                                .message("***** Payment is done ****")
                                                .build();

                kafkaTemplate2.send("payment-topic", psEvent.getTrainName(), psEvent);
            } 
            else
            {
                PaymentEvent pcEvent = new PaymentEvent().builder()
                                            .trainId(seatForPayment.getTrainId())
                                            .trainName(seatForPayment.getTrainName())
                                            .status(PaymentStatus.PAYMENT_CANCEL)
                                            .message("***** Not enough Balance, Payment not done ****")
                                            .build();
                
                log.info("Payment is NOT being done for customer id {} ", customer.get().getCustomerId());

                // informing that payment is cancelled
                kafkaTemplate2.send("payment-topic", pcEvent.getTrainName(), pcEvent);
            }
        }

    }

    @KafkaListener(topics = "Reservation-event-topic", groupId = "paymentReverse")
    public void listenPaymentReverse(String s) throws JsonMappingException, JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();

        ReservationStatusEvent rEvent = mapper.readValue(s, ReservationStatusEvent.class);
        
        if(rEvent.getStatus().equals(ReservationStatus.CANCELED))
        {
            // need to add money back to database
            int customerId = rEvent.getCustomerId();

            Optional<PaymentData> customer = paymentRepo.findById(customerId);
            int newAmount = customer.get().getBalance() + rEvent.getFare();
            customer.get().setBalance(newAmount);

            paymentRepo.save(customer.get());
            System.out.println("Payment reverted back successfully ******* ");
        }
    }
}
