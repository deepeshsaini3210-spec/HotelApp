package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.ReservationController;
import com.grandstay.hotel.service.ReservationService;
import com.grandstay.hotel.util.wrappers.reservationRequest;
import com.grandstay.hotel.util.wrappers.reservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationControllerControllerImp implements ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Override
    public reservationResponse createCustomerAndUpdate(reservationRequest reservation) {
        return reservationService.createCustomerAndUpdate(reservation);
    }

    @Override
    public reservationResponse getReservationById(Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @Override
    public List<reservationResponse> getReservationByCustomerId(Long customerId) {
        return reservationService.getReservationByCustomerId(customerId);
    }

    @Override
    public Boolean reservationCancel(Long reservationId) {
        return reservationService.cancelReservation();
    }
}

