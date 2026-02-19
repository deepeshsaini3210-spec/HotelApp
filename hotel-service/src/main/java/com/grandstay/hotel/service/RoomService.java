package com.grandstay.hotel.service;

import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.util.wrappers.RoomResponse;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    public List<RoomResponse> roomAvailable(String city, LocalDate checkInDate, LocalDate checkOutDate, Room.RoomType roomType);

     public RoomResponse roomById(Long roomId);
}
