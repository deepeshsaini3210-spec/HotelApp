package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.exceptions.BadRequestException;
import com.grandstay.hotel.exceptions.ResourceNotFoundException;
import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.service.RoomManagementService;
import com.grandstay.hotel.util.wrappers.RoomRequest;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.UpdateRoomRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.grandstay.hotel.util.wrappers.ReservationResponse;
import com.grandstay.hotel.model.Reservation;

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
            throw new BadRequestException("RoomRequest cannot be null");
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
    public RoomResponse updateRoom(Long roomId, UpdateRoomRequest request) {
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new ResourceNotFoundException("Room", roomId);
        if (request != null) {
            if (request.getStatus() != null && !request.getStatus().isBlank()) {
                room.setStatus(Room.RoomStatus.valueOf(request.getStatus().trim().toUpperCase()));
            }
            if (request.getPricePerNight() != null && request.getPricePerNight().compareTo(BigDecimal.ZERO) >= 0) {
                room.setPricePerNight(request.getPricePerNight());
            }
        }
        entityManager.merge(room);
        return mapToRoomResponse(room);
    }

    @Override
    public RoomResponse deleteRoomsById(Long roomId) {
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new ResourceNotFoundException("Room", roomId);
        // Block delete only if any reservation is CONFIRMED or CHECKED_IN (active)
        if (room.getReservations() != null) {
            boolean hasActive = room.getReservations().stream()
                    .anyMatch(res -> res.getStatus() == Reservation.ReservationStatus.CONFIRMED
                            || res.getStatus() == Reservation.ReservationStatus.CHECKED_IN);
            if (hasActive) {
                throw new com.grandstay.hotel.exceptions.ConflictException("Cannot delete room with CONFIRMED or CHECKED_IN reservation. Complete or cancel them first.");
            }
            // Detach past reservations from room so we can delete the room
            for (Reservation res : new ArrayList<>(room.getReservations())) {
                res.setRoom(null);
                entityManager.merge(res);
            }
        }
        if (room.getHousekeepingTasks() != null && !room.getHousekeepingTasks().isEmpty()) {
            throw new com.grandstay.hotel.exceptions.ConflictException("Cannot delete room with pending housekeeping tasks");
        }
        RoomResponse resp = mapToRoomResponse(room);
        entityManager.remove(room);
        return resp;
    }

    @Override
    public RoomResponse updateRoomsPrice(Long roomId) {
        // Simple example: increase price by 10%
        com.grandstay.hotel.model.Room room = entityManager.find(com.grandstay.hotel.model.Room.class, roomId);
        if (room == null) throw new ResourceNotFoundException("Room", roomId);
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
        if (room == null) throw new ResourceNotFoundException("Room", roomId);
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
        if (room.getReservations() != null) {
            List<ReservationResponse> resList = new ArrayList<>();
            for (Reservation r : room.getReservations()) {
                ReservationResponse rr = new ReservationResponse();
                rr.setReservationId(r.getReservationId());
                if (r.getUser() != null) rr.setCustomer(r.getUser().getUserId());
                rr.setRoomId(room.getRoomId());
                rr.setRoomNumber(room.getRoomNumber());
                rr.setCheckInDate(r.getCheckInDate());
                rr.setCheckOutDate(r.getCheckOutDate());
                rr.setStatus(r.getStatus());
                if (r.getBilling() != null) rr.setBillingId(r.getBilling().getBillingId());
                resList.add(rr);
            }
            response.setReservations(resList);
        }
        if (room.getHousekeepingTasks() != null) {
            List<Long> hkIds = new ArrayList<>();
            room.getHousekeepingTasks().forEach(h -> hkIds.add(h.getTaskId()));
            response.setHousekeepingTasks(hkIds);
        }
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());
        return response;
    }
}
