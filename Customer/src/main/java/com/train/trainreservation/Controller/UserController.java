package com.train.trainreservation.Controller;

import com.train.trainreservation.Entity.CancelDummyTicket;

import com.train.trainreservation.Entity.Address;
import com.train.trainreservation.Entity.DummySeat;
import com.train.trainreservation.Entity.Customer;
import com.train.trainreservation.Entity.DummyCustomer;
import com.train.trainreservation.Entity.FeignServiceClient;
import com.train.trainreservation.Entity.ReservationCart;
import com.train.trainreservation.Entity.SeatLoc;
import com.train.trainreservation.Enum.ReservationStatus;
import com.train.trainreservation.Repository.ReservationCartRepository;
import com.train.trainreservation.Service.CustomerService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static int cartId = 0;
    private static int seatId = 0;

    @Autowired
    private CustomerService cService;

    @Autowired
    private FeignServiceClient fclient;

    @Autowired
    private ReservationCartRepository reservationCartRepo;


    public static void delay(int second) 
    {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    
    }

    @PostMapping("/add/customer")
    public CompletableFuture<Customer> addCustomer(@RequestBody DummyCustomer dc) throws InterruptedException, ExecutionException
    {
         CompletableFuture<Customer> future = buildAddress(dc)
                                        .thenCompose(add -> {
                                             return buildCustomer(dc,add);
                                        });
        cService.savingCustomer(future);
        return future;        
    }

    public CompletableFuture<Address> buildAddress(DummyCustomer dc)
    {
        return CompletableFuture.supplyAsync(
            () -> {
                
                    Address add = Address.builder()
                                         .zipcode(dc.getZipcode())
                                         .city(dc.getCity())
                                         .build();
                    return add;
            }
        );
    }

    public CompletableFuture<Customer> buildCustomer(DummyCustomer dc, Address add)
    {
        return CompletableFuture.supplyAsync(
            () -> {
                
                Customer cus = Customer.builder()
                                        .customerId(dc.getCustomerId())
                                        .fname(dc.getFname())
                                        .status(dc.getStatus())
                                        .address(add)
                                        .cart(new ReservationCart(++cartId))
                                        .build();
                return cus;
            }
        );
    }

    // @PostMapping("/add/customer")
    // public void addCustomer(@RequestBody DummyCustomer dc)
    // {
    //     Address add = Address.builder()
    //                         .zipcode(dc.getZipcode())
    //                         .city(dc.getCity())
    //                         .build();

    //     Customer cus = Customer.builder()
    //                             .customerId(dc.getCustomerId())
    //                             .fname(dc.getFname())
    //                             .status(dc.getStatus())
    //                             .address(add)
    //                             .cart(new ReservationCart(++cartId))
    //                             .build();
    //     cService.savingCustomer(cus);
    // }

    @GetMapping("/search/reservation/{trainId}")
    public void searchReservation(@PathVariable ("trainId") int trainId)
    {
        fclient.searchSeat(trainId);
    }

    @PostMapping("/cancel/ticket")
    public void cancelConfirmTicket(@RequestBody CancelDummyTicket cTicket)
    {
        fclient.cancelTicket(cTicket);
    }

    @GetMapping("/get/customer/{id}")
    public void getCustomer(@PathVariable("id") int id)
    {
        Customer c = cService.getCustomer(id);
        System.out.println("Customer name is  : " + c.getFname());
    }

    @PostMapping("/book/ticket")
    public void bookTicket(@RequestBody DummySeat dSeat)
    {
        cService.bookTicket(dSeat);
    }


//     @PostMapping("/book/ticket")
//     public void bookTicket(@RequestBody DummySeat dSeat)
//     {
//         long start = System.currentTimeMillis();

//         bookSeat(dSeat);
//         accessReservationCart(dSeat);

//         delay(3);
//         long end = System.currentTimeMillis();

//         System.out.println("Total time : " + (end - start) / 1000);
//     }

//      // booking a seat
//     public  CompletableFuture<Void> bookSeat(DummySeat dSeat) 
//     {
//         return CompletableFuture.runAsync(
//                 () -> {
//                         delay(2);
//                         fclient.BookSeat(dSeat);
//                     }
//             );
//     }

//     public  CompletableFuture<Void> accessReservationCart(DummySeat dSeat) 
//     {
//         return CompletableFuture.runAsync(
//                 () -> {
//                         delay(2);
//                         cService.reservingreservationCart(dSeat);
//                     }
//             );
//     }
}
