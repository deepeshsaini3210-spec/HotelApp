package com.grandstay.hotel.controller;

import com.grandstay.hotel.util.wrappers.BillingResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


public interface BillingController {

    // POST /billing/generate/{reservationId}
    // GET  /billing/{reservationId}


    @PostMapping("/generate/{reservationId}")
    public BillingResponse generateBilling(
            @PathVariable Long reservationId);

    @PostMapping("/generate/checkout/{reservationId}")
    public BillingResponse generateBillingForCheckout(
            @PathVariable Long reservationId);

    @GetMapping("/{reservationId}")
    public BillingResponse getBilling(
            @PathVariable Long reservationId) ;


    
}
