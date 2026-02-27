package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.RoomController;
import com.grandstay.hotel.service.RoomService;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomControllerControllerImp implements RoomController {
    @Autowired
    private RoomService roomService;

    @Override
    public List<RoomResponse> roomAvailable(String city, LocalDate checkInDate, LocalDate checkOutDate, com.grandstay.hotel.model.Room.RoomType roomType) {
        return roomService.roomAvailable(city, checkInDate, checkOutDate, roomType);
    }

    @Override
    public RoomResponse roomById(Long roomId) {
        return roomService.roomById(roomId);
    }
}
