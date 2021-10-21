package com.train.trainreservation.Service;

import com.train.trainreservation.Repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

import com.train.trainreservation.Entity.DummySeat;
import com.train.trainreservation.Entity.ReservationCart;
import com.train.trainreservation.Entity.SeatLoc;
import com.train.trainreservation.Enum.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.train.trainreservation.Repository.ReservationCartRepository;

import com.train.trainreservation.Entity.Customer;

@Service
@Slf4j
public class CustomerService {
    
    private static int cartId = 0;
    private static int seatId = 0;

    @Autowired
    KafkaTemplate<String,DummySeat> kafkaTemplate;

    @Autowired
    private ReservationCartRepository reservationCartRepo;

    @Autowired
    private CustomerRepository cRepo;

    //@PostMapping("/save/customer")
    public void savingCustomer(CompletableFuture<Customer> c) throws InterruptedException, ExecutionException
    {
        cRepo.save(c.get());
    }

    public Customer getCustomer(int id)
    {
        return cRepo.getCustomerById(id);
    }

    public void reservingreservationCart(DummySeat dSeat)
    {
        Customer c = getCustomer(dSeat.getCustomerId());

        List<Integer> row = new ArrayList<>();
        List<Integer> col = new ArrayList<>();

        for(int i = 0 ; i < dSeat.row.length; i++)
        {
            row.add(dSeat.row[i]);
        }
 
        for(int i = 0 ; i < dSeat.column.length; i++)
        {
            col.add(dSeat.column[i]);
        }

        for(int i = 0 ; i < row.size(); i++)
        {
            SeatLoc s = new SeatLoc().builder()
                            .seat_Id(++seatId)
                            .ro(row.get(i))
                            .co(col.get(i))
                            .build();

            List<SeatLoc> list = new ArrayList();
            list.add(s);

            ReservationCart cart = new ReservationCart().builder()
                                    .cart_id(c.getCart().getCart_id())
                                    .trainId(dSeat.getTrainId())
                                    .trainName(dSeat.getTrainName())
                                    .source(dSeat.getSource())
                                    .destination(dSeat.getDestination())
                                    .fare(dSeat.getFare())
                                    .rStatus(ReservationStatus.CURRENT)
                                    .seatLoc(list)
                                    .time(LocalTime.now())
                                    .date(LocalDate.of(2021, 12, 22))
                                    .build();
            
            s.setRCart(cart);
            reservationCartRepo.save(cart);
            System.out.println("Save done...... !");
        }
    }

    public void bookTicket(DummySeat dSeat)
    {
        String trainName = dSeat.getTrainName();
        
        ListenableFuture<SendResult<String, DummySeat>> listenableFuture = kafkaTemplate.send("book-ticket-topic", trainName, dSeat);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, DummySeat>>(){

            @Override
            public void onSuccess(SendResult<String, DummySeat> result) {
                handleSuccess(trainName,dSeat,result);
            }
 
            @Override
            public void onFailure(Throwable ex) {
                
                handleFailure(trainName,dSeat,ex);
            }   
        });
    }

    protected void handleFailure(String trainName, DummySeat dSeat, Throwable ex) {

        log.error("Error sending the message and the exception is {} ", ex.getMessage());
    }

    protected void handleSuccess(String trainName, DummySeat dSeat, SendResult<String, DummySeat> result) {
       
        log.info("Message sent successfully for the key {}  and the value is {} ", trainName, dSeat);
    }
}
