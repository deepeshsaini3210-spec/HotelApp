package com.grandstay.hotel.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grandstay.annotations.SFApiResponses;
import com.grandstay.hotel.util.wrappers.RoomRequest;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.UpdateRoomRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface RoomManagementController {

//     PUT    /rooms/{roomId}
//     DELETE /rooms/{roomId}
//     PUT /rooms/{roomId}/price
//     PUT /rooms/{roomId}/status


    @SFApiResponses
    @PostMapping("/create")
    public RoomResponse createRoom(@RequestBody RoomRequest roomRequest);

    @SFApiResponses
    @GetMapping("/")
    public List<RoomResponse> getRoom();

    @SFApiResponses
    @PutMapping("/{roomId}")
    public RoomResponse updateRoom(@PathVariable Long roomId, @RequestBody(required = false) UpdateRoomRequest request);

    @SFApiResponses
    @DeleteMapping("/{roomId}")
    public RoomResponse deleteRoomsById(@PathVariable Long roomId);

    @SFApiResponses
    @PutMapping("/{roomId}/price")
    public RoomResponse updateRoomsPrice(@PathVariable Long roomId);

    @SFApiResponses
    @PutMapping("/{roomId}/status")
    public RoomResponse updateRoomstatus(@PathVariable Long roomId, @RequestParam String status);

}
