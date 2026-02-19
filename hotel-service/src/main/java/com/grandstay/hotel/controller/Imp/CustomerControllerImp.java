package com.grandstay.hotel.controller.Imp;

import com.grandstay.hotel.controller.CustomersController;
import com.grandstay.hotel.model.Reservation.ReservationStatus;
import com.grandstay.hotel.service.CustomerService;
import com.grandstay.hotel.util.wrappers.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerControllerImp implements CustomersController {

    @Autowired
    private CustomerService customerService;

    @Override
    public UserResponse getCustomerById(Long id) {
        return customerService.getCustomerById(id);
    }

    @Override
    public List<UserResponse> getCustomerBooking() {
        return customerService.getCustomerBooking();
    }
    @Override
    public UserResponse getCustomerBookingById(Long id,ReservationStatus status,LocalDate checkInDate) {
        return customerService.getCustomerBookingById(id,status,checkInDate);
    }

    @Override
    public List<UserResponse> getCustomerHistory() {
        return customerService.getCustomerHistory();
    }
}
