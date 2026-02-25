package com.grandstay.hotel.util.wrappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.grandstay.hotel.util.wrappers.reservationResponse;
import com.grandstay.hotel.model.Room.RoomStatus;
import com.grandstay.hotel.model.Room.RoomType;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class RoomResponse {
    private Long roomId;

    private String roomNumber;

    private RoomType roomType;

    private BigDecimal pricePerNight;

    private RoomStatus status;

    private String hotelCity;

    private List<reservationResponse> reservations;

    private List<Long> housekeepingTasks;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
