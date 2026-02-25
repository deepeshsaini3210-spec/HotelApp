package com.grandstay.hotel.service;

import com.grandstay.hotel.generic.IBaseService;
import com.grandstay.hotel.model.Reservation;
import com.grandstay.hotel.util.wrappers.reservationResponse;
import com.grandstay.hotel.util.wrappers.reservationRequest;

import java.util.List;

public interface ReservationService extends IBaseService<Reservation, Long> {

    reservationResponse createCustomerAndUpdate(reservationRequest reservation);

    reservationResponse updateReservation();

    boolean cancelReservation(Long reservationId);

    reservationResponse getReservationById(Long id);

    List<reservationResponse> getReservationByCustomerId(Long customerId);
}
