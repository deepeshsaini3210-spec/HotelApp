package com.grandstay.hotel.util.wrappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.grandstay.hotel.model.Billing;
import com.grandstay.hotel.model.HousekeepingTask;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.model.Reservation.ReservationStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class reservationResponse {

    private Long reservationId;
    private Long customer;

    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;


    private ReservationStatus status;


    private Billing billing;

    private List<HousekeepingTask> housekeepingTasks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
