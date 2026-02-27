package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.BillingController;
import com.grandstay.hotel.service.BillingService;
import com.grandstay.hotel.util.wrappers.BillingResponse;
import com.grandstay.hotel.util.wrappers.BillingSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingControllerControllerImp implements BillingController {
    @Autowired
    private BillingService billingService;

    @Override
    public BillingResponse generateBilling(Long reservationId) {
        return billingService.generateBilling(reservationId);
    }

    @Override
    public BillingResponse generateBillingForCheckout(Long reservationId) {
        return billingService.generateBillingForCheckout(reservationId);
    }

    @Override
    public BillingResponse getBilling(Long reservationId) {
        return billingService.getBilling(reservationId);
    }

    @Override
    public List<BillingResponse> getPaidBillings() {
        return billingService.getPaidBillings();
    }

    @Override
    public BillingSummaryResponse getBillingSummary() {
        return billingService.getBillingSummary();
    }
}

