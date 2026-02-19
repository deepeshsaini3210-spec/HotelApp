package com.grandstay.hotel.controller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.grandstay.annotations.SFApiResponses;
import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.reservationResponse;


@FeignClient(name = "frontDeskController",url ="${hotel-app-service.url}" ,path="frontdesk")
public interface FrontdeskController {

//     POST /frontdesk/checkin/{reservationId}
//     POST /frontdesk/checkout/{reservationId}
//     GET /frontdesk/reservations/today
//     PUT /frontdesk/assign-room/{reservationId}
    @SFApiResponses
    @PostMapping("/checkin/{reservationId}")
    public reservationResponse getCheckin(@PathVariable Long reservationId);

    @SFApiResponses
    @PostMapping("/checkout/{reservationId}")
    public reservationResponse getCheckout(@PathVariable Long reservationId);

    @SFApiResponses
    @GetMapping("/reservations/today")
    public List<reservationResponse> getReservationToday();

    @SFApiResponses
    @PutMapping("/assign-room/{reservationId}")
    public List<RoomResponse> frontSidebookroom();


}
