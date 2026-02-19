package com.grandstay.hotel.service;

import java.util.List;

import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.reservationResponse;

public interface FrontDeskService {
    public reservationResponse getCheckin(Long reservationId);
    public reservationResponse getCheckout(Long reservationId);
    public List<reservationResponse> getReservationToday();
    public List<RoomResponse> frontSidebookroom();
    
}
