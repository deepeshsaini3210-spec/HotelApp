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
        User user = authService.findByIdOrThrow(request.getCustomer());

        Room room = entityManager.find(Room.class, request.getRoom());
        if (room == null) {
            throw new RuntimeException("Room not found with id: " + request.getRoom());
        }

        if (room.getStatus() != Room.RoomStatus.AVAILABLE) {
            throw new RuntimeException("Room is not available");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        // persist reservation
        save(reservation);

        // mark room as booked
        room.setStatus(Room.RoomStatus.BOOKED);
        entityManager.merge(room);

        return mapToReservationResponse(reservation);
    }

    @Override
    public reservationResponse updateReservation() {
        return null;
    }

    @Override
    public boolean cancelReservation() {
        return false;
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
        response.setCustomer(reservation.getUser().getUserId());
        response.setRoom(reservation.getRoom());
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setStatus(reservation.getStatus());
        response.setBilling(reservation.getBilling());
        return response;
    }
}
