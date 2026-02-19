package com.grandstay.hotel.service;


import com.grandstay.hotel.generic.IBaseService;
import com.grandstay.hotel.generic.Impl.BaseServiceImp;
import com.grandstay.hotel.model.Reservation.ReservationStatus;
import com.grandstay.hotel.model.User;
import com.grandstay.hotel.util.wrappers.UserResponse;

import java.time.LocalDate;
import java.util.List;

public interface CustomerService  {
    
    public UserResponse getCustomerById(Long id);
    public List<UserResponse> getCustomerBooking();
    public List<UserResponse> getCustomerHistory();
    public UserResponse getCustomerBookingById(Long id,ReservationStatus status,LocalDate checkInDate);

}
