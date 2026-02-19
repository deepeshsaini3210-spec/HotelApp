package com.grandstay.hotel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.grandstay.hotel.model.Room.RoomType;
import com.grandstay.hotel.util.wrappers.RoomResponse;

public interface RoomController {

//     POST   /rooms
// GET    /rooms
// PUT    /rooms/{roomId}
// DELETE /rooms/{roomId}


    @GetMapping("/room/available")
    public List<RoomResponse> roomAvailable(
        @RequestParam String city,
        @RequestParam LocalDate checkInDate,
        @RequestParam(required = false) LocalDate checkOutDate,
        @RequestParam(defaultValue = "SINGLE") RoomType roomType
    );

    @GetMapping("/room/{roomId}")
    public RoomResponse roomById(
        @PathVariable Long roomId
    );

}
