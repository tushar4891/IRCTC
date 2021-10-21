package com.train.trainreservation.Entity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "trainData-ws", url = "http://localhost:9091")
public interface FeignServiceClient {
     
    @GetMapping("/search/seat/{trainId}")
    public void searchSeat(@PathVariable("trainId") int trainId);

    @PostMapping("/cancel")
    public void cancelTicket(@RequestBody CancelDummyTicket ticket);

    @PostMapping(value="/bookseat")
    public void BookSeat(@RequestBody DummySeat dSeat);
}
