package com.grandstay.hotel.service;

import java.util.List;

import com.grandstay.hotel.util.wrappers.RoomResponse;
import com.grandstay.hotel.util.wrappers.ReservationResponse;

public interface FrontDeskService {
    public ReservationResponse getCheckin(Long reservationId);
    public ReservationResponse getCheckout(Long reservationId);
    public List<ReservationResponse> getReservationToday();
    public List<RoomResponse> frontSidebookroom();
    
}
