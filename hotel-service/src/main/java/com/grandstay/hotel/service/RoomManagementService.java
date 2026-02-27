package com.grandstay.hotel.service;


import com.grandstay.hotel.util.wrappers.RoomRequest;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.UpdateRoomRequest;

import java.util.List;

public interface RoomManagementService {
    RoomResponse createRoom(RoomRequest roomRequest);
    List<RoomResponse> getRoom();
    RoomResponse updateRoom(Long roomId, UpdateRoomRequest request);
    RoomResponse deleteRoomsById(Long roomId);
    RoomResponse updateRoomsPrice(Long roomId);
    RoomResponse updateRoomstatus(Long roomId, String status);
}
