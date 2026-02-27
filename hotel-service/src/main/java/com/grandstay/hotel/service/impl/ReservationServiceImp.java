package com.grandstay.hotel.service.imp;

import com.grandstay.hotel.exceptions.BadRequestException;
import com.grandstay.hotel.exceptions.ConflictException;
import com.grandstay.hotel.exceptions.ResourceNotFoundException;
import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.service.ReservationService;
import com.grandstay.hotel.util.wrappers.ReservationResponse;
import com.grandstay.hotel.util.wrappers.ReservationRequest;
import com.grandstay.hotel.model.Room;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.integration.Service.StorageMiniOService;
import com.grandstay.hotel.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private StorageMiniOService storageMiniOService;
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
    public ReservationResponse createCustomerAndUpdate(MultipartFile file, ReservationRequest request) {
        if (request == null) {
            throw new BadRequestException("ReservationRequest cannot be null");
        }
        if (request.getCustomer() == null) {
            throw new BadRequestException("customer id is required");
        }
        if (request.getRoom() == null) {
            throw new BadRequestException("room id is required");
        }
        if (request.getCheckInDate() == null || request.getCheckOutDate() == null) {
            throw new BadRequestException("checkInDate and checkOutDate are required");
        }
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new BadRequestException("checkOutDate must be after checkInDate");
        }
        if (file == null) {
            throw new BadRequestException("file is required");
        }

        User user = authService.findByIdOrThrow(request.getCustomer());
        Room room = entityManager.find(Room.class, request.getRoom());
        if (room == null) {
            throw new ResourceNotFoundException("Room", request.getRoom());
        }
        if (room.getStatus() != Room.RoomStatus.AVAILABLE) {
            throw new ConflictException("Room is not available for booking");
        }

        String fileUrl;
        try {
            fileUrl = storageMiniOService.uploadFile(file);
        } catch (Exception e) {
            throw new BadRequestException("Failed to upload file: " + e.getMessage());
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setFileUrl(fileUrl);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        save(reservation);
        room.setStatus(Room.RoomStatus.BOOKED);
        entityManager.merge(room);

        return mapToReservationResponse(reservation);
    }

    @Override
    public ReservationResponse updateReservation() {
        return null;
    }

    @Override
    public boolean cancelReservation(Long reservationId) {
        Reservation reservation = findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", reservationId));
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
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = findByIdOrThrow(id);
        return mapToReservationResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getReservationByCustomerId(Long customerId) {
        return entityManager.createNamedQuery("findByUserId", Reservation.class)
                .setParameter("userId", customerId)
                .getResultList().stream()
                .map(this::mapToReservationResponse)
                .toList();
    }

    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setReservationId(reservation.getReservationId());
        response.setCustomer(reservation.getUser() != null ? reservation.getUser().getUserId() : null);
        if (reservation.getRoom() != null) {
            response.setRoomId(reservation.getRoom().getRoomId());
            response.setRoomNumber(reservation.getRoom().getRoomNumber());
        }
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setStatus(reservation.getStatus());
        response.setFileUrl(reservation.getFileUrl());
        response.setBillingId(reservation.getBilling() != null ? reservation.getBilling().getBillingId() : null);
        response.setCreatedAt(reservation.getCreatedAt());
        response.setUpdatedAt(reservation.getUpdatedAt());
        return response;
    }
}
