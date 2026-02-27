package com.grandstay.hotel.util.wrappers;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomRequest {
    private String status;
    private BigDecimal pricePerNight;
}
