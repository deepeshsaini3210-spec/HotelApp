package com.grandstay.hotel.util.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/** Paid amounts by month (key: "yyyy-MM"), and total unpaid roomCharges. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingSummaryResponse {

    /** Month-wise paid revenue: key = "yyyy-MM", value = sum of roomCharges for that month. */
    private Map<String, BigDecimal> paidByMonth;

    /** Total roomCharges for all unpaid (PENDING) billings. */
    private BigDecimal unpaidTotal;
}
