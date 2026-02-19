package com.grandstay.hotel.service.imp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.service.FrontDeskService;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.reservationResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FrontDeskServiceImp implements FrontDeskService {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public reservationResponse getCheckin(Long reservationId) {
        Reservation reservation = entityManager.createNamedQuery("findById", Reservation.class)
                .setParameter("reservationId", reservationId)
                .getSingleResult();
        // Must be CONFIRMED to check-in; cannot check-in twice
        if (reservation.getStatus() == Reservation.ReservationStatus.CHECKED_IN) {
            throw new RuntimeException("Reservation already checked in");
        }
        if (reservation.getStatus() != Reservation.ReservationStatus.CONFIRMED) {
            throw new RuntimeException("Reservation is not in a state that allows check-in. Expected CONFIRMED");
        }

        reservation.setStatus(Reservation.ReservationStatus.CHECKED_IN);
        entityManager.merge(reservation);
        return mapToReservationResponse(reservation);
    }

    @Override
    public reservationResponse getCheckout(Long reservationId) {
        Reservation reservation=entityManager.createNamedQuery("findById", Reservation.class)
                .setParameter("reservationId", reservationId)
                .getSingleResult();
        reservation.setStatus(Reservation.ReservationStatus.CHECKED_OUT);
        reservation.setCheckOutDate(LocalDate.now());
        entityManager.merge(reservation);
        return mapToReservationResponse(reservation);
    }

    @Override
    public List<reservationResponse> getReservationToday() {
        List<Reservation> reservation=entityManager.createNamedQuery("findToday",Reservation.class)
                .setParameter("CURRENT_DATE",LocalDate.now())
                .getResultList();
        List<reservationResponse> responseList=new ArrayList<>();
        for(Reservation reservations:reservation){
            responseList.add(mapToReservationResponse(reservations));
        }
        return responseList;
    }

    @Override
    public List<RoomResponse> frontSidebookroom() {
        List<Room> room =entityManager.createNamedQuery("findAvailable",Room.class)
                .getResultList();
        List<RoomResponse> roomResponses=new ArrayList<>();
        for(Room rooms:room){
            roomResponses.add(mapToRoomResponse( rooms));
        }
        return roomResponses;
    }

    private RoomResponse mapToRoomResponse(Room room ){

        RoomResponse response=new RoomResponse();
        response.setRoomId(room.getRoomId());
        response.setReservations(room.getReservations());
        response.setStatus(room.getStatus());
        response.setRoomNumber(room.getRoomNumber());
        response.setRoomType(room.getRoomType());
        response.setHotelCity(room.getHotelCity());
        response.setCreatedAt(room.getCreatedAt());
        response.setUpdatedAt(room.getUpdatedAt());
        response.setReservations(room.getReservations());
        return response;
    }
}
