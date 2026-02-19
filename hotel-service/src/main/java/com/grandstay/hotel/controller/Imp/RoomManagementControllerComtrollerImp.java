package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.RoomManagementController;
import com.grandstay.hotel.service.RoomManagementService;
import com.grandstay.hotel.util.wrappers.RoomRequest;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RoomManagementControllerComtrollerImp implements RoomManagementController {

    @Autowired
    private RoomManagementService roomManagementService;

    @Override
    public RoomResponse createRoom(@RequestBody RoomRequest roomRequest) {
       
        return roomManagementService.createRoom(roomRequest);
    }

    @Override
    public List<RoomResponse> getRoom() {
        return roomManagementService.getRoom();
    }

    @Override
    public RoomResponse updateRoomsById(Long roomId) {
        return roomManagementService.updateRoomsById(roomId);
    }

    @Override
    public RoomResponse deleteRoomsById(Long roomId) {
        return roomManagementService.deleteRoomsById(roomId);
    }

    @Override
    public RoomResponse updateRoomsPrice(Long roomId) {
        return roomManagementService.updateRoomsPrice(roomId);
    }

    @Override
    public RoomResponse updateRoomstatus(Long roomId,String status) {
        return roomManagementService.updateRoomstatus(roomId,status);
    }
}

