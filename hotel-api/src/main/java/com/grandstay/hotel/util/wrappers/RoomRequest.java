package com.grandstay.hotel.util.wrappers;

import java.math.BigDecimal;
import java.util.List;

import com.grandstay.hotel.model.HousekeepingTask;
import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.model.Room.RoomStatus;
import com.grandstay.hotel.model.Room.RoomType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String roomNumber;

    private String roomType;

    private String reservations;

    private String housekeepingTasks;

    private BigDecimal pricePerNight;

    private String status;

    private String hotelCity;

    

}
