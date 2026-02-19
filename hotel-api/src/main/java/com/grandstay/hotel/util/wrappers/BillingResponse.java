package com.grandstay.hotel.util.wrappers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BillingResponse {

    private Long billingId;
    private Long reservationId;

    private BigDecimal roomCharges;
    private BigDecimal extraCharges;
    private BigDecimal discount;
    private BigDecimal totalAmount;

    private String paymentStatus;
    private LocalDateTime createdAt;


}
