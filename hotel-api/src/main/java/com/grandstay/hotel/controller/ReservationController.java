package com.grandstay.hotel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.grandstay.hotel.util.wrappers.reservationRequest;
import com.grandstay.hotel.util.wrappers.reservationResponse;

public interface ReservationController {
    // POST   /reservations
    // GET    /reservations/{reservationId}
    // GET    /reservations/customer/{customerId}
    // PUT    /reservations/{reservationId}/cancel

    @PostMapping("/create/reservation")
    public reservationResponse createCustomerAndUpdate(
        @RequestBody reservationRequest reservation
    );
    
    @GetMapping("/reservations/{reservationId}")
    public reservationResponse getReservationById(@PathVariable Long reservationId);

    @GetMapping("/reservations/customer/{customerId}")
    public List<reservationResponse> getReservationByCustomerId(@PathVariable Long customerId);

    @PutMapping("/reservations/{reservationId}/cancel")
    public Boolean reservationCancel(@PathVariable Long reservationId);

}
