package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.service.ReservationService;
import com.grandstay.hotel.util.wrappers.reservationResponse;
import com.grandstay.hotel.util.wrappers.reservationRequest;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImp extends BaseServiceImp<Reservation, Long> implements ReservationService {

    public ReservationServiceImp( EntityManager entityManager) {
        super(entityManager, Reservation.class, Reservation::getReservationId);
    }

    @Autowired
    private AuthService authService;
    @Override
    public Optional<Reservation> findById(Long id) {
        List<Reservation> list = entityManager.createNamedQuery("findById", Reservation.class)
                .setParameter("reservationId", id)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Reservation> findAll() {
        return entityManager.createNamedQuery("findAllReservations", Reservation.class).getResultList();
    }

    @Override
    public reservationResponse createCustomerAndUpdate(reservationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("reservationRequest cannot be null");
        }
        if (request.getCustomer() == null) {
            throw new IllegalArgumentException("customer id is required");
        }
        if (request.getRoom() == null) {
            throw new IllegalArgumentException("room id is required");
        }
        if (request.getCheckInDate() == null || request.getCheckOutDate() == null) {
            throw new IllegalArgumentException("checkInDate and checkOutDate are required");
        }
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }

        User user = authService.findByIdOrThrow(request.getCustomer());
        Room room = entityManager.find(Room.class, request.getRoom());
        if (room == null) {
            throw new RuntimeException("Room not found with id: " + request.getRoom());
        }
        if (room.getStatus() != Room.RoomStatus.AVAILABLE) {
            throw new RuntimeException("Room is not available for booking");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        save(reservation);
        room.setStatus(Room.RoomStatus.BOOKED);
        entityManager.merge(room);

        return mapToReservationResponse(reservation);
    }

    @Override
    public reservationResponse updateReservation() {
        return null;
    }

    @Override
    public boolean cancelReservation(Long reservationId) {
        Reservation reservation = findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found: " + reservationId));
        if (reservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
            return true;
        }
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        entityManager.merge(reservation);
        Room room = reservation.getRoom();
        if (room != null && room.getStatus() == Room.RoomStatus.BOOKED) {
            room.setStatus(Room.RoomStatus.AVAILABLE);
            entityManager.merge(room);
        }
        return true;
    }

    @Override
    public reservationResponse getReservationById(Long id) {
        Reservation reservation = findByIdOrThrow(id);
        return mapToReservationResponse(reservation);
    }

    @Override
    public List<reservationResponse> getReservationByCustomerId(Long customerId) {
        return entityManager.createNamedQuery("findByUserId", Reservation.class)
                .setParameter("userId", customerId)
                .getResultList().stream()
                .map(this::mapToReservationResponse)
                .toList();
    }

    private reservationResponse mapToReservationResponse(Reservation reservation) {
        reservationResponse response = new reservationResponse();
        response.setReservationId(reservation.getReservationId());
        response.setCustomer(reservation.getUser() != null ? reservation.getUser().getUserId() : null);
        if (reservation.getRoom() != null) {
            response.setRoomId(reservation.getRoom().getRoomId());
            response.setRoomNumber(reservation.getRoom().getRoomNumber());
        }
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setStatus(reservation.getStatus());
        response.setBillingId(reservation.getBilling() != null ? reservation.getBilling().getBillingId() : null);
        response.setCreatedAt(reservation.getCreatedAt());
        response.setUpdatedAt(reservation.getUpdatedAt());
        return response;
    }
}
