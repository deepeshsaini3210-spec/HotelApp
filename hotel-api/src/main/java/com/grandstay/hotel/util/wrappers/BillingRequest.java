package com.grandstay.hotel.util.wrappers;
import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BillingRequest {

    private BigDecimal roomCharges;
    private BigDecimal extraCharges;
    private BigDecimal discount;

    
}
