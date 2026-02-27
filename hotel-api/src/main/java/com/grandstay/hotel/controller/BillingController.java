package com.grandstay.hotel.controller;

import com.grandstay.hotel.util.wrappers.BillingResponse;
import com.grandstay.hotel.util.wrappers.BillingSummaryResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface BillingController {

    @PostMapping("/generate/{reservationId}")
    BillingResponse generateBilling(@PathVariable Long reservationId);

    @PostMapping("/generate/checkout/{reservationId}")
    BillingResponse generateBillingForCheckout(@PathVariable Long reservationId);

    @GetMapping("/{reservationId}")
    BillingResponse getBilling(@PathVariable Long reservationId);

    @GetMapping("/revenue/paid")
    List<BillingResponse> getPaidBillings();

    @GetMapping("/revenue/summary")
    BillingSummaryResponse getBillingSummary();
}
