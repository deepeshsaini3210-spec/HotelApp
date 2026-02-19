package com.grandstay.hotel.util.wrappers;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class reservationRequest {

    private Long room;
    private Long customer;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}
