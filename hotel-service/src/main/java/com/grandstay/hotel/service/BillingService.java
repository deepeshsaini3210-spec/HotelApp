package com.grandstay.hotel.service;

import com.grandstay.hotel.generic.IBaseService;
import com.grandstay.hotel.model.Billing;
import com.grandstay.hotel.util.wrappers.BillingResponse;
import com.grandstay.hotel.util.wrappers.BillingSummaryResponse;

import java.util.List;

public interface BillingService extends IBaseService<Billing, Long> {

    BillingResponse generateBilling(Long reservationId);

    BillingResponse generateBillingForCheckout(Long reservationId);

    BillingResponse getBilling(Long reservationId);

    List<BillingResponse> getPaidBillings();

    BillingSummaryResponse getBillingSummary();
}
