package com.grandstay.hotel.controller.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grandstay.hotel.controller.FrontdeskController;
import com.grandstay.hotel.service.FrontDeskService;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.reservationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontdesk")
public class FrontdeskControllerControllerImp implements FrontdeskController {

    @Autowired
    private FrontDeskService frontDeskService;
    
    @Override
    public reservationResponse getCheckin(Long reservationId) {
        return frontDeskService.getCheckin(reservationId);
    }

    @Override
    public reservationResponse getCheckout(Long reservationId) {
        return frontDeskService.getCheckout(reservationId);
    }

    @Override
    public List<reservationResponse> getReservationToday() {
        return frontDeskService.getReservationToday();
    }

    @Override
    public List<RoomResponse> frontSidebookroom() {
        return frontDeskService.frontSidebookroom();
    }
}
