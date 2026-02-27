package com.grandstay.hotel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.grandstay.hotel.util.wrappers.ReservationRequest;
import com.grandstay.hotel.util.wrappers.ReservationResponse;

public interface ReservationController {

    /** Create reservation with image upload. Mapping is on the implementation to avoid AbstractMethodError with multipart. */
    ReservationResponse createCustomerAndUpdate(org.springframework.web.multipart.MultipartFile file, ReservationRequest reservation);

    @GetMapping("/{reservationId:[0-9]+}")
    ReservationResponse getReservationById(@PathVariable Long reservationId);

    @GetMapping("/customer/{customerId}")
    public List<ReservationResponse> getReservationByCustomerId(@PathVariable Long customerId);

    @PutMapping("/{reservationId}/cancel")
    public Boolean reservationCancel(@PathVariable Long reservationId);

}
