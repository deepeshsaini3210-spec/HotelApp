package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.service.RoomManagementService;
import com.grandstay.hotel.util.wrappers.RoomRequest;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
public class RoomManagementServiceImp extends BaseServiceImp<Room, Long> implements RoomManagementService {


    @PersistenceContext
    private EntityManager entityManager;

    public RoomManagementServiceImp(EntityManager entityManager) {
        super(entityManager, Room.class, Room::getRoomId);
    }

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        if (roomRequest == null) {
            throw new IllegalArgumentException("RoomRequest cannot be null");
        }
        Room.RoomType roomType = parseRoomType(roomRequest.getRoomType());
        Room.RoomStatus status = parseRoomStatus(roomRequest.getStatus());
        Room room = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomType)
                .pricePerNight(roomRequest.getPricePerNight())
                .status(status)
                .hotelCity(roomRequest.getHotelCity())
                .build();
        save(room);
        return mapToRoomResponse(room);
    }

    private static Room.RoomType parseRoomType(String value) {
        if (value == null || value.isBlank()) return Room.RoomType.SINGLE;
        return Room.RoomType.valueOf(value.trim().toUpperCase());
    }

    private static Room.RoomStatus parseRoomStatus(String value) {
        if (value == null || value.isBlank()) return Room.RoomStatus.AVAILABLE;
        return Room.RoomStatus.valueOf(value.trim().toUpperCase());
    }

    @Override
    public List<RoomResponse> getRoom() {
        List<com.grandstay.hotel.model.Room> rooms = entityManager.createQuery("SELECT r FROM Room r", Room.class)
                .getResultList();
        return rooms.stream().map(this::mapToRoomResponse).toList();
    }

    @Override
    public RoomResponse updateRoomsById(Long roomId) {
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new RuntimeException("Room not found: " + roomId);
        entityManager.merge(room);
        return mapToRoomResponse(room);
    }

    @Override
    public RoomResponse deleteRoomsById(Long roomId) {
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new RuntimeException("Room not found: " + roomId);
        RoomResponse resp = mapToRoomResponse(room);
        entityManager.remove(room);
        return resp;
    }

    @Override
    public RoomResponse updateRoomsPrice(Long roomId) {
        // Simple example: increase price by 10%
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new RuntimeException("Room not found: " + roomId);
        if (room.getPricePerNight() != null) {
            java.math.BigDecimal current = room.getPricePerNight();
            java.math.BigDecimal increased = current.multiply(java.math.BigDecimal.valueOf(1.10));
            room.setPricePerNight(increased);
            entityManager.merge(room);
        }
        return mapToRoomResponse(room);
    }

    @Override
    public RoomResponse updateRoomstatus(Long roomId,String status) {
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new RuntimeException("Room not found: " + roomId);
        room.setStatus(Room.RoomStatus.valueOf(status.trim().toUpperCase()));
        entityManager.merge(room);
        return mapToRoomResponse(room);
    }

    private RoomResponse mapToRoomResponse(com.grandstay.hotel.model.Room room) {
        RoomResponse response = new RoomResponse();
        response.setRoomId(room.getRoomId());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setPricePerNight(room.getPricePerNight());
        response.setStatus(room.getStatus());
        response.setHotelCity(room.getHotelCity());
        response.setReservations(room.getReservations());
        response.setHousekeepingTasks(room.getHousekeepingTasks());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());
        return response;
    }
}
