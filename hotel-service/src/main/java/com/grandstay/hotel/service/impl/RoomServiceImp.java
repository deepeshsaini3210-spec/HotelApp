package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.exceptions.ResourceNotFoundException;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.service.RoomService;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import com.grandstay.hotel.util.wrappers.ReservationResponse;
import com.grandstay.hotel.model.Reservation;


@Service
public class RoomServiceImp implements RoomService {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<RoomResponse> roomAvailable(String city, LocalDate checkInDate, LocalDate checkOutDate, Room.RoomType roomType) {
        EntityManager em = entityManager;
        StringBuilder sb = new StringBuilder();
        // Only consider non-CANCELLED reservations for date overlap, so cancelled rooms show as available
        sb.append("SELECT DISTINCT r FROM Room r WHERE r.status = 'AVAILABLE'");
        if (city != null && !city.isBlank()) sb.append(" AND r.hotelCity = :city");
        if (roomType != null) sb.append(" AND r.roomType = :roomType");
        // Only exclude rooms that have CONFIRMED or CHECKED_IN reservation overlapping the dates
        if (checkInDate != null && checkOutDate != null) {
            sb.append(" AND NOT EXISTS (SELECT 1 FROM Reservation res WHERE res.room = r AND res.status IN ('CONFIRMED', 'CHECKED_IN') AND res.checkInDate <= :checkOut AND res.checkOutDate >= :checkIn)");
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
        if (room == null) throw new ResourceNotFoundException("Room", roomId);
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
        if (room.getReservations() != null) {
            List<ReservationResponse> resList = new ArrayList<>();
            for (Reservation r : room.getReservations()){
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
