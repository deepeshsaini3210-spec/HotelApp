package com.grandstay.hotel.service;

import com.grandstay.hotel.generic.IBaseService;
import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.util.wrappers.ReservationResponse;
import com.grandstay.hotel.util.wrappers.ReservationRequest;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ReservationService extends IBaseService<Reservation, Long> {

    ReservationResponse createCustomerAndUpdate(MultipartFile file, ReservationRequest reservation);

    ReservationResponse updateReservation();

    boolean cancelReservation(Long reservationId);

    ReservationResponse getReservationById(Long id);

    List<ReservationResponse> getReservationByCustomerId(Long customerId);
}
