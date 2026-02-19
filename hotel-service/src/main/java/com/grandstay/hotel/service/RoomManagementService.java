package com.grandstay.hotel.service;


import com.grandstay.hotel.util.wrappers.RoomRequest;
import com.grandstay.hotel.util.wrappers.RoomResponse;

import java.util.List;

public interface RoomManagementService {
    public RoomResponse createRoom(RoomRequest roomRequest);
    public List<RoomResponse> getRoom();
    public RoomResponse updateRoomsById(Long roomId);
    public RoomResponse deleteRoomsById(Long roomId);
    public RoomResponse updateRoomsPrice(Long roomId);
    public RoomResponse updateRoomstatus(Long roomId,String status);
}
