package com.grandstay.hotel.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.grandstay.annotations.SFApiResponses;
import com.grandstay.hotel.model.Reservation.ReservationStatus;
import com.grandstay.hotel.util.wrappers.UserResponse;


@FeignClient(name = "customerController", url = "${hotel-app-service.url}", path = "/customer")
public interface CustomersController {

    
    @SFApiResponses
    @GetMapping("/{id}")
    public UserResponse getCustomerById(@PathVariable Long id);

    @SFApiResponses
    @GetMapping("/booking")
    public List<UserResponse> getCustomerBooking();

    @SFApiResponses
    @GetMapping("/booking/{id}")
    public UserResponse getCustomerBookingById(@PathVariable Long id,ReservationStatus status,LocalDate checkInDate);
    
    @SFApiResponses
    @GetMapping("/history")
    public List<UserResponse> getCustomerHistory();




}
