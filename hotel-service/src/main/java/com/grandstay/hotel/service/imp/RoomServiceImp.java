package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.service.RoomService;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class RoomServiceImp implements RoomService {
    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager entityManager;
    @Override
    public List<RoomResponse> roomAvailable(String city, LocalDate checkInDate, LocalDate checkOutDate, Room.RoomType roomType) {
        jakarta.persistence.EntityManager em = entityManager;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT r FROM Room r LEFT JOIN r.reservations res WHERE r.status = 'AVAILABLE'");
        if (city != null && !city.isBlank()) sb.append(" AND r.hotelCity = :city");
        if (roomType != null) sb.append(" AND r.roomType = :roomType");
        if (checkInDate != null && checkOutDate != null) {
            sb.append(" AND (res IS NULL OR NOT (res.checkInDate <= :checkOut AND res.checkOutDate >= :checkIn))");
        }
        var q = em.createQuery(sb.toString(), Room.class);
        if (city != null && !city.isBlank()) q.setParameter("city", city);
        if (roomType != null) q.setParameter("roomType", roomType);
        if (checkInDate != null && checkOutDate != null) {
            q.setParameter("checkIn", checkInDate);
            q.setParameter("checkOut", checkOutDate);
        }
        List<Room> rooms = q.getResultList();
        return rooms.stream().map(this::mapToResponse).toList();
    }

    @Override
    public RoomResponse roomById(Long roomId) {
        jakarta.persistence.EntityManager em = entityManager;
        Room room = em.find(Room.class, roomId);
        if (room == null) throw new RuntimeException("Room not found: " + roomId);
        return mapToResponse(room);
    }

    private RoomResponse mapToResponse(Room room) {
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
