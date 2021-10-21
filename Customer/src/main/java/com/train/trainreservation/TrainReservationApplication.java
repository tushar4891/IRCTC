package com.train.trainreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableDiscoveryClient
public class TrainReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainReservationApplication.class, args);
	}
}
